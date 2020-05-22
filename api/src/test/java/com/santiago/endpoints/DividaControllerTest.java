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
import com.santiago.builders.DividaBuilder;
import com.santiago.builders.PagamentoBuilder;
import com.santiago.domain.Divida;
import com.santiago.domain.Pagamento;
import com.santiago.dtos.DividaDTO;
import com.santiago.dtos.PagamentoDTO;
import com.santiago.endpoints.enuns.TipoEndPoint;
import com.santiago.services.DividaService;
import com.santiago.services.PagamentoService;
import com.santiago.services.exceptions.ObjectNotFoundException;
import com.santiago.services.interfaces.IServiceCrud;

@RunWith(SpringRunner.class)
@WebMvcTest(DividaController.class)
public class DividaControllerTest extends BaseControllerTest<Divida, DividaDTO> {

	@MockBean
	private DividaService dividaService;

	@MockBean
	private PagamentoService pagamentoService;

	private Pagamento pagamento;
	private PagamentoDTO pagamentoDTO;
	private PagamentoDTO pagamentoDTOInvalido;
	private List<Pagamento> pagamentos;

	@Override
	public void setup() {
		super.setup();
		this.pagamento = PagamentoBuilder.mockPagamentoBuilder().getPagamento();
		this.pagamentoDTO = PagamentoBuilder.mockPagamentoDTOBuilder().getPagamentoDTO();
		this.pagamentoDTOInvalido = PagamentoBuilder.mockPagamentoDTOBuilder().getPagamentoDTOInvalido();
		this.pagamentos = PagamentoBuilder.mockCollectionPagamentosBuilder().getPagamentos();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPagamentos() throws Exception {
		when(this.pagamentoService.findAllPagamentoByDividaId(1L)).thenReturn(this.pagamentos);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.get(TipoEndPoint.DIVIDA + TipoEndPoint.DIVIDA_ID + TipoEndPoint.PAGAMENTO, 1)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPagePagamentos() throws Exception {
		Page<Pagamento> entityPage = new PageImpl<>(this.pagamentos);

		when(this.pagamentoService.findPageByDividaId(1L, 0, 24, "ASC", "nome")).thenReturn(entityPage);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.get(TipoEndPoint.DIVIDA + TipoEndPoint.DIVIDA_ID + TipoEndPoint.PAGAMENTO + TipoEndPoint.PAGE, 1L)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarPagamento() throws Exception {
		when(this.pagamentoService.findById(1L)).thenReturn(this.pagamento);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.DIVIDA + TipoEndPoint.PAGAMENTO + TipoEndPoint.ID, 1L)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
	}

	@Test
	public void deveRetornarNaoEncontrado_QuandoBuscarPagamento() throws Exception {
		when(this.pagamentoService.findById(2L)).thenThrow(ObjectNotFoundException.class);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.DIVIDA + TipoEndPoint.PAGAMENTO + TipoEndPoint.ID, 2L)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void deveRetornarSucesso_QuandoInserirPagamento() throws Exception {
		when(this.pagamentoService.fromDTO(this.pagamentoDTO)).thenReturn(this.pagamento);
		when(this.pagamentoService.insert(this.pagamento)).thenReturn(this.pagamento);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post(TipoEndPoint.DIVIDA + TipoEndPoint.DIVIDA_ID + TipoEndPoint.PAGAMENTO, 1)
						.content(this.jsonParse(this.pagamentoDTO)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void deveRetornarNaoProcessada_QuandoInserirPagamentoInvalido() throws Exception {
		Set<ConstraintViolation<PagamentoDTO>> constraintViolations = validator.validate(this.pagamentoDTOInvalido);

		assertFalse(constraintViolations.isEmpty());
	}

	@Test
	public void deveRetornarSucesso_QuandoDeletarPagamento() throws Exception {
		doNothing().when(this.pagamentoService).delete(1L);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.delete(TipoEndPoint.DIVIDA + TipoEndPoint.PAGAMENTO + TipoEndPoint.ID, 1L)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Override
	public String getRoute() {
		return TipoEndPoint.DIVIDA;
	}

	@Override
	public Divida mockEntityBuilder() {
		return DividaBuilder.mockDividaBuilder().getDivida();
	}

	@Override
	public DividaDTO mockEntityDTOBuilder() {
		return DividaBuilder.mockDividaDTOBuilder().getDividaDTO();
	}

	@Override
	public DividaDTO mockEntityDTOInvalidaBuilder() {
		return DividaBuilder.mockDividaDTOBuilder().getDividaDTOInvalido();
	}

	@Override
	public List<Divida> mockCollectionEntityListBuilder() {
		return DividaBuilder.mockCollectionDividasBuilder().getDividas();
	}

	@Override
	public IServiceCrud<Divida, DividaDTO> getService() {
		return this.dividaService;
	}

	@Override
	protected String jsonParse(DividaDTO objDTO) {
		String dataDividaOld = "\"dataDivida\":{\"year\":2020,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfMonth\":1,\"dayOfWeek\":\"WEDNESDAY\",\"dayOfYear\":1,\"leapYear\":true,\"monthValue\":1,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}}";
		String dataDividaNew = "\"dataDivida\": \"01/02/2019\"";
		return super.jsonParse(objDTO).replace(dataDividaOld, dataDividaNew);
	}

	private String jsonParse(PagamentoDTO objDTO) {
		String dataOld = "\"data\":{\"year\":2020,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfMonth\":1,\"dayOfWeek\":\"WEDNESDAY\",\"dayOfYear\":1,\"leapYear\":true,\"monthValue\":1,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}}";
		String dataNew = "\"data\": \"01/02/2019\"";
		try {
			return new ObjectMapper().writeValueAsString(objDTO).replace(dataOld, dataNew);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}
}
