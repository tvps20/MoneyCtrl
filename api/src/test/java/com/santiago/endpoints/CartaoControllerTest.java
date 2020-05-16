package com.santiago.endpoints;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.santiago.builders.CartaoBuilder;
import com.santiago.domain.Cartao;
import com.santiago.dtos.CartaoDTO;
import com.santiago.endpoints.enuns.TipoEndPoint;
import com.santiago.services.CartaoService;
import com.santiago.services.interfaces.IServiceCrud;

@RunWith(SpringRunner.class)
@WebMvcTest(CartaoController.class)
public class CartaoControllerTest extends BaseControllerTest<Cartao, CartaoDTO> {

	@MockBean
	private CartaoService cartaoService;

	private Cartao cartao;
	private CartaoDTO cartaoDTO;

	@Override
	public void setup() {
		super.setup();
		this.cartao = this.mockEntityBuilder();
		this.cartaoDTO = this.mockEntityDTOBuilder();
	}

	@Test
	public void deveRetornarNaoProcessada_QuandoInserirCartaoJaSalvo() throws Exception {
		when(this.cartaoService.verificarCampoUnico(this.cartaoDTO.getNome())).thenReturn(true);
		when(this.cartaoService.fromDTO(this.cartaoDTO)).thenReturn(this.cartao);
		when(this.cartaoService.insert(this.cartao)).thenReturn(this.cartao);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.post(TipoEndPoint.CARTAO).content(this.jsonParse(this.cartaoDTO))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
	}

	@Override
	public String getRoute() {
		return TipoEndPoint.CARTAO;
	}

	@Override
	public Cartao mockEntityBuilder() {
		return CartaoBuilder.mockCartaoBuilder().getCartao();
	}

	@Override
	public CartaoDTO mockEntityDTOBuilder() {
		return CartaoBuilder.mockCartaoDTOBuilder().getCartaoDTO();
	}

	@Override
	public CartaoDTO mockEntityDTOInvalidaBuilder() {
		return CartaoBuilder.mockCartaoDTOBuilder().getCartaoDTOInvalido();
	}

	@Override
	public List<Cartao> mockCollectionEntityListBuilder() {
		return CartaoBuilder.mockCollectionCartoesBuilder().getCartoes();
	}

	@Override
	public IServiceCrud<Cartao, CartaoDTO> getService() {
		return this.cartaoService;
	}
}
