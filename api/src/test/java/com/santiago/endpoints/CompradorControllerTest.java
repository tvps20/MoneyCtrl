package com.santiago.endpoints;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santiago.builders.CompradorBuilder;
import com.santiago.builders.CreditoBuilder;
import com.santiago.domain.Comprador;
import com.santiago.domain.Credito;
import com.santiago.dtos.CompradorDTO;
import com.santiago.dtos.CreditoDTO;
import com.santiago.endpoints.enuns.TipoEndPoint;
import com.santiago.services.CompradorService;
import com.santiago.services.CreditoService;
import com.santiago.services.UsuarioService;
import com.santiago.services.exceptions.ObjectNotFoundException;
import com.santiago.services.interfaces.IServiceCrud;

@RunWith(SpringRunner.class)
@WebMvcTest(CompradorController.class)
public class CompradorControllerTest extends BaseControllerTest<Comprador, CompradorDTO> {

	@MockBean
	private CompradorService compradorService;

	@MockBean
	private CreditoService creditoService;

	@MockBean
	private UsuarioService usuarioService;

	private Credito credito;
	private CreditoDTO creditoDTO;
	private CreditoDTO creditoDTOInvalido;
	private List<Credito> creditos;

	@Override
	public void setup() {
		super.setup();
		this.credito = CreditoBuilder.mockCreditoBuilder().getCredito();
		this.creditoDTO = CreditoBuilder.mockCreditoDTOBuilder().getCreditoDTO();
		this.creditoDTOInvalido = CreditoBuilder.mockCreditoDTOBuilder().getCreditoDTOInvalido();
		this.creditos = CreditoBuilder.mockCollectionCreditosBuilder().getCreditos();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllCreditos() throws Exception {
		when(this.creditoService.findAllCreditoByCompradorId(1L)).thenReturn(this.creditos);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.get(TipoEndPoint.COMPRADOR + TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO, 1)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPageCreditos() throws Exception {
		Page<Credito> entityPage = new PageImpl<>(this.creditos);

		when(this.creditoService.findPageByCompradorId(1L, 0, 24, "ASC", "nome")).thenReturn(entityPage);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.get(TipoEndPoint.COMPRADOR + TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO + TipoEndPoint.PAGE, 1L)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarCredito() throws Exception {
		when(this.creditoService.findById(1L)).thenReturn(this.credito);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.COMPRADOR + TipoEndPoint.CREDITO + TipoEndPoint.ID, 1L)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
	}

	@Test
	public void deveRetornarNaoEncontrado_QuandoBuscarCredito() throws Exception {
		when(this.creditoService.findById(2L)).thenThrow(ObjectNotFoundException.class);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.COMPRADOR + TipoEndPoint.CREDITO + TipoEndPoint.ID, 2L)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void deveRetornarSucesso_QuandoInserirCredito() throws Exception {
		when(this.creditoService.fromDTO(this.creditoDTO)).thenReturn(this.credito);
		when(this.creditoService.insert(this.credito)).thenReturn(this.credito);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.post(TipoEndPoint.COMPRADOR + TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO, 1)
				.content(this.jsonParse(this.creditoDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void deveRetornarNaoProcessada_QuandoInserirCreditoInvalido() throws Exception {
		Set<ConstraintViolation<CreditoDTO>> constraintViolations = validator.validate(this.creditoDTOInvalido);

		assertFalse(constraintViolations.isEmpty());
	}

	@Test
	public void deveRetornarSucesso_QuandoDeletarCredito() throws Exception {
		doNothing().when(this.creditoService).delete(1L);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.delete(TipoEndPoint.COMPRADOR + TipoEndPoint.CREDITO + TipoEndPoint.ID, 1L)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Override
	public String getRoute() {
		return TipoEndPoint.COMPRADOR;
	}

	@Override
	public Comprador mockEntityBuilder() {
		return CompradorBuilder.mockCompradorBuilder().getComprador();
	}

	@Override
	public CompradorDTO mockEntityDTOBuilder() {
		return CompradorBuilder.mockCompradorDTOBuilder().getCompradorDTO();
	}

	@Override
	public CompradorDTO mockEntityDTOInvalidaBuilder() {
		return CompradorBuilder.mockCompradorDTOBuilder().getCompradorDTOInvalido();
	}

	@Override
	public List<Comprador> mockCollectionEntityListBuilder() {
		return CompradorBuilder.mockCollectionCompradoresBuilder().getCompradores();
	}

	@Override
	public IServiceCrud<Comprador, CompradorDTO> getService() {
		return this.compradorService;
	}

	private String jsonParse(CreditoDTO objDTO) {
		String dataOld = "\"data\":{\"year\":2020,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfMonth\":1,\"dayOfWeek\":\"WEDNESDAY\",\"dayOfYear\":1,\"leapYear\":true,\"monthValue\":1,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}}";
		String dataNew = "\"data\": \"01/02/2019\"";
		try {
			return new ObjectMapper().writeValueAsString(objDTO).replace(dataOld, dataNew);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}
}
