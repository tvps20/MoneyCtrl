package com.santiago.moneyctrl.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.moneyctrl.domain.BaseEntity;
import com.santiago.moneyctrl.dtos.BaseDTO;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;
import com.santiago.moneyctrl.util.MensagemUtil;

public abstract class BaseService<T extends BaseEntity, K extends BaseDTO> implements IServiceCrud<T, K> {

	/**
	 * Repositorio interno
	 */
	protected JpaRepository<T, Long> repository;
	protected String entityName = "Entity"; 

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
			throw new ObjectNotFoundException(MensagemUtil.erroObjNotFountId(this.entityName, id));
		}

		baseLog.info("[FindById] - Entidade encontrada com sucesso.");
		return obj.get();
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
	@Override
	public Page<T> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		baseLog.info("[FindPage] - Buscando paginado todas as entidades: { Page: " + page + ", linesPerPage: "
				+ linesPerPage + " }");
		Direction directionParse = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, directionParse, orderBy);
		Page<T> pages = repository.findAll(pageRequest);

		baseLog.info("[FindPage] - Busca paginada finalizada com sucesso.");
		return pages;
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
			baseLog.info("[Insert] - Salvando entidade...");
			T entitySalva = this.repository.save(entity);

			baseLog.info("[Insert] - Entidade salva no bando de dados.");
			return entitySalva;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Insert] - Erro ao tentar salvar " + this.entityName + ".");
			throw new DataIntegrityException(MensagemUtil.erroObjInsert(this.entityName));
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
			baseLog.error("[Update] - Erro ao tentar atualizar " + this.entityName + ".");
			throw new DataIntegrityException(MensagemUtil.erroObjUpdate(this.entityName, entity.getId()));
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
			baseLog.error("[Delete] - Erro ao tentar apagar " + this.entityName + ".");
			throw new DataIntegrityException(MensagemUtil.erroObjDelete(this.entityName, id));
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
