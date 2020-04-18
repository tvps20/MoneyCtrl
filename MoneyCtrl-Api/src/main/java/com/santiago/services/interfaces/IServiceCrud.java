package com.santiago.services.interfaces;

import java.util.List;

import com.santiago.domain.AbstractEntity;
import com.santiago.dtos.BaseDTO;

public interface IServiceCrud<T extends AbstractEntity, K extends BaseDTO> extends IMapperToEntity<K, T> {

	/**
	 * Recupera todas as entidade da base de dados
	 *
	 * @return Uma lista com todas as entidades
	 */
	public List<T> findAll();

	/**
	 * Recupera uma entidade da base de dados
	 *
	 * @param id Idenficador da entidade a ser recuperada
	 * @return Container que encapsula a entidade
	 */
	public T findById(Long id);

	/**
	 * Cria ou atualiza uma entidade
	 *
	 * @param entity Entitdade a ser salva
	 * @return Nova ou atualizada entidade
	 */
	public T insert(T entity);

	/**
	 * Atualiza uma entidade
	 *
	 * @param entity Entitdade a ser atualizada
	 * @return Entitdade Atualizada
	 */
	public T update(T entity);

	/**
	 * Exclui uma entidade da base de dados
	 *
	 * @param id Idenficador da entidade a ser excluida
	 */
	public void delete(Long id);
}
