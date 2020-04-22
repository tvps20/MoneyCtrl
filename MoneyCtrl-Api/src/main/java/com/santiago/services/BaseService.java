package com.santiago.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.domain.BaseEntity;
import com.santiago.dtos.BaseDTO;
import com.santiago.services.exceptions.DataIntegrityException;
import com.santiago.services.exceptions.ObjectNotFoundException;
import com.santiago.services.interfaces.IServiceCrud;
import com.santiago.util.Mensagem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseService<T extends BaseEntity, K extends BaseDTO> implements IServiceCrud<T, K> {

	/**
	 * Repositorio interno
	 */
	protected JpaRepository<T, Long> repository;

	public BaseService(JpaRepository<T, Long> repository) {
		this.repository = repository;
	}

	/**
	 * Recupera todas as entidades tipo T da base de dados
	 *
	 * @return Lista com todos os elementos da base de dados
	 */
	@Override
	public List<T> findAll() {
		log.info("Find All entity: " + this.getTClass().getName());
		return this.repository.findAll();
	}

	/**
	 * Recupera todas as entidades da base de dados de forma paginada.
	 * 
	 * @param page         pagina atual
	 * @param linesPerPage quatidade de elementos por pagina
	 * @param orderBy      campo pelo qual sera feita a ordenação
	 * @param direction    Direção pelo qual os elementos serão retornados,
	 *                     crescente ou decrescente.
	 * @return
	 */
	// TODO: Corrigir problema de busca pela direção
	@Override
	public Page<T> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		log.info("Find page entity: " + this.getTClass().getName());
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		return repository.findAll(pageRequest);
	}

	/**
	 * Recupera uma entidade atravez do seu identificador
	 *
	 * @param id Idenficador da entidade a ser recuperada
	 * @return Container da entidade T
	 */
	@Override
	public T findById(Long id) throws ObjectNotFoundException {
		log.info("Find by id entity: " + id + ": " + this.getTClass().getName());
		Optional<T> obj = this.repository.findById(id);

		if (!obj.isPresent()) {
			log.error(Mensagem.erroObjNotFount(id, this.getTClass().getName()));
			throw new ObjectNotFoundException(Mensagem.erroObjNotFount(id, this.getTClass().getName()));
		}

		return obj.get();
	}

	/**
	 * Cria ou atualiza uma Entidade
	 *
	 * @param dto Dto com as informações a serem transformadas em Entidade
	 * @return Entidade T
	 */
	@Override
	public T insert(T entity) {
		log.info("Insert entity: " + this.getTClass().getName());
		entity.setId(null);
		return this.repository.save(entity);
	}

	/**
	 * Atualiza uma entidade
	 *
	 * @param entity Entitdade a ser atualizada
	 * @return Entitdade Atualizada
	 */
	@Override
	public T update(T entity) {
		log.info("Update entity: " + this.getTClass().getName());
		T newObj = this.findById(entity.getId());
		this.updateData(newObj, entity);
		return this.repository.save(newObj);
	}

	/**
	 * Exclue uma Entidade da base de dados
	 *
	 * @param id Idenficador da Entidade a ser excluida
	 */
	@Override
	public void delete(Long id) {
		this.findById(id);
		try {
			log.info("Delete entity: " + this.getTClass().getName());
			this.repository.deleteById(id);
		} catch (DataIntegrityViolationException ex) {
			log.error(Mensagem.erroObjDelete(this.getTClass().getName()), ex);
			throw new DataIntegrityException(Mensagem.erroObjDelete(this.getTClass().getName()));
		}
	}

	/**
	 * Atualiza o objeto do banco de acordo com as novas informações passadas pelo
	 * outro objeto
	 * 
	 * @param newObj Objeto vindo do banco de dados a ser atualizado
	 * @param obj    Objeto com informações a ser passadas para o newObj
	 */
	public abstract void updateData(T newObj, T obj);

	/**
	 * Método para obter o nome da classe
	 * 
	 * @return uma instancia da classe generica do tipo T
	 */
	public abstract Class<T> getTClass();
}
