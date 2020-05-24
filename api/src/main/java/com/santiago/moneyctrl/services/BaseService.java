package com.santiago.moneyctrl.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.moneyctrl.domain.BaseEntity;
import com.santiago.moneyctrl.dtos.BaseDTO;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;
import com.santiago.moneyctrl.util.Mensagem;

public abstract class BaseService<T extends BaseEntity, K extends BaseDTO> implements IServiceCrud<T, K> {

	/**
	 * Repositorio interno
	 */
	protected JpaRepository<T, Long> repository;

	protected static Logger baseLog = LoggerFactory.getLogger(BaseService.class);

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
		baseLog.info("[FindAll] - Buscando todas as entidades.");
		List<T> list = this.repository.findAll();

		baseLog.info("[FindAll] - Busca finalizada com sucesso.");
		return list;
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
	// https://stackoverflow.com/questions/52687061/spring-data-jpa-method-query-with-paging-gives-me-an-error
	@Override
	public Page<T> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		baseLog.info("[FindPage] - Buscando todas as entidades paginada: { Page: " + page + ", linesPerPage: "
				+ linesPerPage + " }");
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		Page<T> pages = repository.findAll(pageRequest);

		baseLog.info("[FindPage] - Busca paginada finalizada com sucesso.");
		return pages;
	}

	/**
	 * Recupera uma entidade atravez do seu identificador
	 *
	 * @param id Idenficador da entidade a ser recuperada
	 * @return Container da entidade T
	 */
	@Override
	public T findById(Long id) throws ObjectNotFoundException {
		baseLog.info("[FindById] - Buscando entidade pelo Id: " + id);
		Optional<T> obj = this.repository.findById(id);

		if (!obj.isPresent()) {
			baseLog.error("[FindById] - Entidade não encontada.");
			throw new ObjectNotFoundException(Mensagem.erroObjNotFount(id, this.getClass().getName()), this.getClass());
		}

		baseLog.info("[FindById] - Entidade encontrada com sucesso.");
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
		entity.setId(null);
		baseLog.info("[Insert] - Salvando uma nova entidade. Entity: " + entity.toString());
		try {
			T entitySalva = this.repository.save(entity);

			baseLog.info("[Insert] - Entidade salva no bando de dados.");
			return entitySalva;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Insert] - Erro ao tentar salvar entidade.");
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		}
	}

	/**
	 * Atualiza uma entidade
	 *
	 * @param entity Entitdade a ser atualizada
	 * @return Entitdade Atualizada
	 */
	@Override
	public T update(T entity) {
		baseLog.info("[Update] - Atualizando entidade. Entity: " + entity.toString());
		try {
			T newObj = this.findById(entity.getId());
			this.updateData(newObj, entity);
			T entityAtualizada = this.repository.save(newObj);

			baseLog.info("[Update] - Entitade atualizada no bando de dados.");
			return entityAtualizada;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Update] - Erro ao tentar apagar entidade.");
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			baseLog.error("[Update] - Erro ao tentar buscar entidade.");
			throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getId(), this.getClass().getName()),
					this.getClass());
		}
	}

	/**
	 * Exclue uma Entidade da base de dados
	 *
	 * @param id Idenficador da Entidade a ser excluida
	 */
	@Override
	public void delete(Long id) {
		baseLog.info("[Delete] - Apagando entidade de Id: " + id);
		try {
			this.findById(id);
			this.repository.deleteById(id);
			baseLog.info("[Delete] - Entidade apagada com sucesso.");

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Delete] - Erro ao tentar apagar entidade.");
			throw new DataIntegrityException(Mensagem.erroObjDelete(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			baseLog.error("[Delete] - Erro ao tentar buscar entidade.");
			throw new ObjectNotFoundException(Mensagem.erroObjNotFount(id, this.getClass().getName()), this.getClass());
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
}
