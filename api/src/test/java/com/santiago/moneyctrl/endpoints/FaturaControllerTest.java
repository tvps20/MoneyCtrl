package com.santiago.moneyctrl.endpoints;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.santiago.moneyctrl.builders.FaturaBuilder;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.dtos.FaturaDTO;
import com.santiago.moneyctrl.endpoints.FaturaController;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.FaturaService;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(SpringRunner.class)
@WebMvcTest(FaturaController.class)
public class FaturaControllerTest extends BaseControllerTest<Fatura, FaturaDTO> {

	@MockBean
	private FaturaService faturaService;

	@Override
	public String getRoute() {
		return TipoEndPoint.FATURA;
	}

	@Override
	public Fatura mockEntityBuilder() {
		return FaturaBuilder.mockFaturaBuilder().getFatura();
	}

	@Override
	public FaturaDTO mockEntityDTOBuilder() {
		return FaturaBuilder.mockFaturaDTOBuilder().getFaturaDTO();
	}

	@Override
	public FaturaDTO mockEntityDTOInvalidaBuilder() {
		return FaturaBuilder.mockFaturaDTOBuilder().getFaturaDTOInvalido();
	}

	@Override
	public List<Fatura> mockCollectionEntityListBuilder() {
		return FaturaBuilder.mockCollectionFaturasBuilder().getFaturas();
	}

	@Override
	public IServiceCrud<Fatura, FaturaDTO> getService() {
		return this.faturaService;
	}

	@Override
	protected String jsonParse(FaturaDTO objDTO) {
		String vencimentoOld = "\"vencimento\":{\"year\":2020,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfMonth\":1,\"dayOfWeek\":\"WEDNESDAY\",\"dayOfYear\":1,\"leapYear\":true,\"monthValue\":1,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}}";
		String vencimentoNew = "\"vencimento\": \"01/02/2019\"";
		return super.jsonParse(objDTO).replace(vencimentoOld, vencimentoNew);
	}
}
