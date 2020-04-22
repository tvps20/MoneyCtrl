package com.santiago.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.santiago.domain.BaseEntity;
import com.santiago.dtos.BaseDTO;

public interface IServiceCrud<T extends BaseEntity, K extends BaseDTO> extends IMapperToEntity<K, T> {

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
	 * Recupera todas as entidades da pase de dados paginado.
	 * 
	 * @param page pagina atual
	 * @param linesPerPage quantidade de itens por pagina
	 * @param orderBy ordena pela compo especfico.
	 * @param direção da pagina de itens (ASC, DESC)
	 * @return
	 */
	public Page<T> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

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
