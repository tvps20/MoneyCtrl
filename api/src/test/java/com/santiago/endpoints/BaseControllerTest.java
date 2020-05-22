package com.santiago.endpoints;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santiago.domain.BaseEntity;
import com.santiago.dtos.BaseDTO;
import com.santiago.endpoints.enuns.TipoEndPoint;
import com.santiago.services.exceptions.ObjectNotFoundException;
import com.santiago.services.interfaces.IServiceCrud;

public abstract class BaseControllerTest<T extends BaseEntity, K extends BaseDTO> {

	@Autowired
	protected MockMvc mockMvc;

	protected IServiceCrud<T, K> service;

	private T entity;
	private K entityDTO;
	private K entityDTOInvalida;
	private List<T> entityList;
	protected static Validator validator;

	@Before
	public void setup() {
		this.service = this.getService();
		this.entity = this.mockEntityBuilder();
		this.entityDTO = this.mockEntityDTOBuilder();
		this.entityDTOInvalida = this.mockEntityDTOInvalidaBuilder();
		this.entityList = this.mockCollectionEntityListBuilder();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllEntities() throws Exception {
		when(this.service.findAll()).thenReturn(this.entityList);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(this.getRoute()).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPageEntities() throws Exception {
		Page<T> entityListPage = new PageImpl<>(this.entityList);

		when(this.service.findPage(0, 24, "ASC", "nome")).thenReturn(entityListPage);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.get(this.getRoute() + TipoEndPoint.PAGE).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarEntity() throws Exception {
		when(this.service.findById(1L)).thenReturn(this.entity);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.get(this.getRoute() + TipoEndPoint.ID, 1L).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
	}

	@Test
	public void deveRetornarNaoEncontrado_QuandoBuscarEntity() throws Exception {
		when(this.service.findById(2L)).thenThrow(ObjectNotFoundException.class);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.get(this.getRoute() + TipoEndPoint.ID, 2L).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void deveRetornarSucesso_QuandoInserirEntity() throws Exception {
		when(this.service.fromDTO(this.entityDTO)).thenReturn(this.entity);
		when(this.service.insert(this.entity)).thenReturn(this.entity);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.post(this.getRoute()).content(this.jsonParse(this.entityDTO))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void deveRetornarNaoProcessada_QuandoInserirEntityInvalido() throws Exception {
		Set<ConstraintViolation<K>> constraintViolations = validator.validate(this.entityDTOInvalida);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.post(this.getRoute()).content(this.jsonParse(this.entityDTOInvalida))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
		assertFalse(constraintViolations.isEmpty());
	}

	@Test
	public void deveRetornarSucesso_QuandoAtualizarEntity() throws Exception {
		when(this.service.fromDTO(this.entityDTO)).thenReturn(this.entity);
		when(this.service.update(this.entity)).thenReturn(this.entity);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(this.getRoute() + TipoEndPoint.ID, 1L)
				.content(this.jsonParse(this.entityDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deveRetornarSucesso_QuandoDeletarEntity() throws Exception {
		doNothing().when(this.service).delete(1L);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(this.getRoute() + TipoEndPoint.ID, 1L)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deveRetornarNaoEncontrado_QuandoDeletarEntity() throws Exception {
		doThrow(ObjectNotFoundException.class).when(this.service).delete(1L);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(this.getRoute() + TipoEndPoint.ID, 1L)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// Metodos
	protected String jsonParse(final K objDTO) {
		try {
			return new ObjectMapper().writeValueAsString(objDTO);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}

	public abstract String getRoute();

	public abstract T mockEntityBuilder();

	public abstract K mockEntityDTOBuilder();

	public abstract K mockEntityDTOInvalidaBuilder();

	public abstract List<T> mockCollectionEntityListBuilder();

	public abstract IServiceCrud<T, K> getService();
}
