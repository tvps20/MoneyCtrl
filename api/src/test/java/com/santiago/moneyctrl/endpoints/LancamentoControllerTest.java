package com.santiago.moneyctrl.endpoints;

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
import com.santiago.moneyctrl.builders.CotaBuilder;
import com.santiago.moneyctrl.builders.LancamentoBuilder;
import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.dtos.CotaDTO;
import com.santiago.moneyctrl.dtos.LancamentoDTO;
import com.santiago.moneyctrl.endpoints.LancamentoController;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.CotaService;
import com.santiago.moneyctrl.services.LancamentoService;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(SpringRunner.class)
@WebMvcTest(LancamentoController.class)
public class LancamentoControllerTest extends BaseControllerTest<Lancamento, LancamentoDTO> {

	@MockBean
	private LancamentoService lancamentoService;

	@MockBean
	private CotaService cotaService;

	private Cota cota;
	private CotaDTO cotaDTO;
	private CotaDTO cotaDTOInvalido;
	private List<Cota> cotas;

	@Override
	public void setup() {
		super.setup();
		this.cota = CotaBuilder.mockCotaBuilder().getCota();
		this.cotaDTO = CotaBuilder.mockCotaDTOBuilder().getCotaDTO();
		this.cotaDTOInvalido = CotaBuilder.mockCotaDTOBuilder().getCotaDTOInvalido();
		this.cotas = CotaBuilder.mockCollectionCotasBuilder().getCotas();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPagamentos() throws Exception {
		when(this.cotaService.findAllCotaByLancamentoId(1L)).thenReturn(this.cotas);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.get(TipoEndPoint.LANCAMENTO + TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA, 1)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPagePagamentos() throws Exception {
		Page<Cota> entityPage = new PageImpl<>(this.cotas);

		when(this.cotaService.findPageCotaByLancamentoId(1L, 0, 24, "ASC", "nome")).thenReturn(entityPage);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.get(TipoEndPoint.LANCAMENTO + TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA + TipoEndPoint.PAGE, 1L)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarPagamento() throws Exception {
		when(this.cotaService.findById(1L)).thenReturn(this.cota);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.LANCAMENTO + TipoEndPoint.COTA + TipoEndPoint.ID, 1L)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
	}

	@Test
	public void deveRetornarNaoEncontrado_QuandoBuscarPagamento() throws Exception {
		when(this.cotaService.findById(2L)).thenThrow(ObjectNotFoundException.class);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.LANCAMENTO + TipoEndPoint.COTA + TipoEndPoint.ID, 2L)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void deveRetornarSucesso_QuandoInserirPagamento() throws Exception {
		when(this.cotaService.fromDTO(this.cotaDTO)).thenReturn(this.cota);
		when(this.cotaService.insert(this.cota)).thenReturn(this.cota);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.post(TipoEndPoint.LANCAMENTO + TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA, 1)
						.content(this.jsonParse(this.cotaDTO)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void deveRetornarNaoProcessada_QuandoInserirPagamentoInvalido() throws Exception {
		Set<ConstraintViolation<CotaDTO>> constraintViolations = validator.validate(this.cotaDTOInvalido);

		assertFalse(constraintViolations.isEmpty());
	}

	@Test
	public void deveRetornarSucesso_QuandoDeletarPagamento() throws Exception {
		doNothing().when(this.cotaService).delete(1L);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.delete(TipoEndPoint.LANCAMENTO + TipoEndPoint.COTA + TipoEndPoint.ID, 1L)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Override
	public String getRoute() {
		return TipoEndPoint.LANCAMENTO;
	}

	@Override
	public Lancamento mockEntityBuilder() {
		return LancamentoBuilder.mockLancamentoBuilder().getLancamento();
	}

	@Override
	public LancamentoDTO mockEntityDTOBuilder() {
		return LancamentoBuilder.mockLancamentoDTOBuilder().getLancamentoDTO();
	}

	@Override
	public LancamentoDTO mockEntityDTOInvalidaBuilder() {
		return LancamentoBuilder.mockLancamentoDTOBuilder().getLancamentoDTOInvalido();
	}

	@Override
	public List<Lancamento> mockCollectionEntityListBuilder() {
		return LancamentoBuilder.mockCollectionLancamentosBuilder().getLancamentos();
	}

	@Override
	public IServiceCrud<Lancamento, LancamentoDTO> getService() {
		return this.lancamentoService;
	}

	@Override
	protected String jsonParse(LancamentoDTO objDTO) {
		String dataCompraOld = "\"dataCompra\":{\"year\":2020,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfMonth\":1,\"dayOfWeek\":\"WEDNESDAY\",\"dayOfYear\":1,\"leapYear\":true,\"monthValue\":1,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}}";
		String dataCompraNew = "\"dataCompra\": \"01/02/2019\"";
		return super.jsonParse(objDTO).replace(dataCompraOld, dataCompraNew);
	}

	private String jsonParse(CotaDTO objDTO) {
		try {
			return new ObjectMapper().writeValueAsString(objDTO);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}
}
