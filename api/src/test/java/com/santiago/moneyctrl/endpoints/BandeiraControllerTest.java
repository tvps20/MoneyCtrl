package com.santiago.moneyctrl.endpoints;

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

import com.santiago.moneyctrl.builders.BandeiraBuilder;
import com.santiago.moneyctrl.domain.Bandeira;
import com.santiago.moneyctrl.dtos.BandeiraDTO;
import com.santiago.moneyctrl.endpoints.BandeiraController;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.BandeiraService;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(SpringRunner.class)
@WebMvcTest(BandeiraController.class)
public class BandeiraControllerTest extends BaseControllerTest<Bandeira, BandeiraDTO> {

	@MockBean
	private BandeiraService bandeiraService;

	private Bandeira bandeira;
	private BandeiraDTO bandeiraDTO;

	@Override
	public void setup() {
		super.setup();
		this.bandeira = this.mockEntityBuilder();
		this.bandeiraDTO = this.mockEntityDTOBuilder();
//		ReflectionTestUtils.setField(customUniqueValidator, "context", context);
	}

	@Test
	public void deveRetornarNaoProcessada_QuandoInserirBadeiraJaSalva() throws Exception {
		when(this.bandeiraService.verificarCampoUnico(this.bandeiraDTO.getNome())).thenReturn(true);
		when(this.bandeiraService.fromDTO(this.bandeiraDTO)).thenReturn(this.bandeira);
		when(this.bandeiraService.insert(this.bandeira)).thenReturn(this.bandeira);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.post(TipoEndPoint.BANDEIRA).content(this.jsonParse(this.bandeiraDTO))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
	}

	@Override
	public String getRoute() {
		return TipoEndPoint.BANDEIRA;
	}

	@Override
	public Bandeira mockEntityBuilder() {
		return BandeiraBuilder.mockBandeiraBuilder().getBandeira();
	}

	@Override
	public BandeiraDTO mockEntityDTOBuilder() {
		return BandeiraBuilder.mockBandeiraDTOBuilder().getBandeiraDTO();
	}

	@Override
	public BandeiraDTO mockEntityDTOInvalidaBuilder() {
		return BandeiraBuilder.mockBandeiraDTOBuilder().getBandeiraDTOInvalido();
	}

	@Override
	public List<Bandeira> mockCollectionEntityListBuilder() {
		return BandeiraBuilder.mockCollectionBandeirasBuilder().getBandeiras();
	}

	@Override
	public IServiceCrud<Bandeira, BandeiraDTO> getService() {
		return this.bandeiraService;
	}
}
