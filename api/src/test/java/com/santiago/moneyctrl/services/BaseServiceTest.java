package com.santiago.moneyctrl.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.moneyctrl.domain.BaseEntity;
import com.santiago.moneyctrl.dtos.BaseDTO;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

public abstract class BaseServiceTest<T extends BaseEntity, K extends BaseDTO> {

	protected IServiceCrud<T, K> service;

	protected JpaRepository<T, Long> repository;

	protected T entity;
	private Optional<T> entityOpt;
	protected List<T> entityList;

	@Before
	public void setup() {
		this.service = this.getService();
		this.repository = this.getRepository();
		this.entity = this.mockEntityBuilder();
		this.entityOpt = this.mockEntityOptBuilder();
		this.entityList = this.mockCollectionEntityListBuilder();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllEntities() {
		when(this.repository.findAll()).thenReturn(this.entityList);

		List<T> result = this.service.findAll();

		assertFalse(result.isEmpty());
		assertEquals(result.size(), 10);
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPageEntities() {
		Page<T> bandeiraListPage = new PageImpl<>(this.entityList);
		PageRequest pageRequest = PageRequest.of(0, 24);

		when(this.repository.findAll(pageRequest)).thenReturn(bandeiraListPage);

		List<T> result = this.service.findPage(0, 24, "ASC", "nome").getContent();

		assertFalse(result.isEmpty());
		assertEquals(result.size(), 10);
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarEntity() {
		when(this.repository.findById(1L)).thenReturn(this.entityOpt);

		T result = this.service.findById(1L);

		assertEquals(result, this.entityOpt.get());
		assertEquals(result.getId(), this.entityOpt.get().getId());
	}

	@Test(expected = ObjectNotFoundException.class)
	public void deveRetornarException_QuandoBuscarEntity() {
		when(this.repository.findById(1L)).thenReturn(Optional.empty());

		this.service.findById(1L);

		verify(this.service, Mockito.times(1)).findById(1L);
	}

	@Test
	public void deveRetornarSucesso_QuandoInserirEntity() {
		when(this.repository.save(this.entity)).thenReturn(this.entity);

		T result = this.service.insert(this.entity);

		assertEquals(result, this.entity);
		assertEquals(result.getId(), this.entity.getId());
	}

	@Test(expected = DataIntegrityException.class)
	public void deveRetornarException_QuandoInserirEntity() {
		doThrow(DataIntegrityViolationException.class).when(this.repository).save(this.entity);

		this.service.insert(this.entity);

		verify(this.service, Mockito.times(1)).insert(this.entity);
	}

	@Test
	public void deveRetornarSucesso_QuandoAtualizarEntity() {
		T entityUpdate = this.getEntityUpdate();

		when(this.repository.findById(1L)).thenReturn(this.entityOpt);
		when(this.repository.save(entityUpdate)).thenReturn(entityUpdate);

		T result = this.service.update(this.entity);

		assertEquals(result, entityUpdate);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void deveRetornarException_QuandoAtualizarEntity() {
		when(this.repository.findById(1L)).thenReturn(Optional.empty());

		this.service.update(this.entity);

		verify(this.repository, Mockito.times(1)).findById(1L);
	}

	@Test
	public void deveRetornarSucesso_QuandoDeletarEntity() {
		when(this.repository.findById(1L)).thenReturn(this.entityOpt);

		this.service.delete(1L);

		verify(this.repository, Mockito.times(1)).findById(1L);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void deveRetornarException_QuandoDeletarEntity() {
		when(this.repository.findById(1L)).thenReturn(Optional.empty());

		this.service.delete(1L);

		verify(this.repository, Mockito.times(1)).findById(1L);
	}

	public abstract T mockEntityBuilder();

	public abstract Optional<T> mockEntityOptBuilder();

	public abstract List<T> mockCollectionEntityListBuilder();

	public abstract IServiceCrud<T, K> getService();

	public abstract JpaRepository<T, Long> getRepository();

	public abstract T getEntityUpdate();
}
