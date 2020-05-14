package com.santiago.endpoints;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santiago.builders.BandeiraBuilder;
import com.santiago.domain.Bandeira;
import com.santiago.dtos.BandeiraDTO;
import com.santiago.endpoints.enuns.TipoEndPoint;
import com.santiago.services.BandeiraService;
import com.santiago.services.exceptions.ObjectNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(BandeiraController.class)
public class BandeiraControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BandeiraService bandeiraService;

//	@Autowired
//	private ApplicationContext context;

//    private static Validator validator;

	private Bandeira bandeira;
	private BandeiraDTO bandeiraDTO;
	private List<Bandeira> bandeiras;

	@Before
	public void setup() {
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//		ReflectionTestUtils.setField(customUniqueValidator, "context", context);
		this.bandeira = BandeiraBuilder.mockBandeiraBuilder().getBandeira();
		this.bandeiraDTO = BandeiraBuilder.mockBandeiraDTOBuilder().getBandeiraDTO();
		this.bandeiras = (List<Bandeira>) BandeiraBuilder.mockCollectionBandeirasBuilder().getBandeiras();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllBadeiras() throws Exception {
		when(this.bandeiraService.findAll()).thenReturn(this.bandeiras);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.bandeira).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllBadeirasPage() throws Exception {
		Page<Bandeira> bandeirasPage = new PageImpl<>(this.bandeiras);

		when(this.bandeiraService.findPage(0, 24, "ASC", "nome")).thenReturn(bandeirasPage);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.bandeiraComPage).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", is(1)));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarBadeira() throws Exception {
		when(this.bandeiraService.findById(1L)).thenReturn(new Bandeira(1L, "mastercard"));

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.bandeiraComId, 1L).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("mastercard"));
	}

	@Test
	public void deveRetornarNaoEncontrado_QuandoBuscarBadeira() throws Exception {
		when(this.bandeiraService.findById(2L)).thenThrow(ObjectNotFoundException.class);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TipoEndPoint.bandeiraComId, 2L).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void deveRetornarSucesso_QuandoInserirBadeira() throws Exception {
//		Set<ConstraintViolation<BandeiraDTO>> constraintViolations = validator.validate(bandeiraDTO);

		when(this.bandeiraService.fromDTO(this.bandeiraDTO)).thenReturn(this.bandeira);
		when(this.bandeiraService.insert(this.bandeira)).thenReturn(this.bandeira);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.post(TipoEndPoint.bandeira).content(this.jsonParse(this.bandeiraDTO))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void deveRetornarSucesso_QuandoAtualizarBandeira() throws Exception {
		when(this.bandeiraService.fromDTO(this.bandeiraDTO)).thenReturn(this.bandeira);
		when(this.bandeiraService.update(this.bandeira)).thenReturn(this.bandeira);

		ResultActions result = mockMvc.perform(
				MockMvcRequestBuilders.put(TipoEndPoint.bandeiraComId, 1L).content(this.jsonParse(this.bandeiraDTO))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deveRetornarSucesso_QuandoDeletarBadeira() throws Exception {
		doNothing().when(this.bandeiraService).delete(1L);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(TipoEndPoint.bandeiraComId, 1L)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deveRetornarNaoEncontrado_QuandoDeletarBadeira() throws Exception {
		doThrow(ObjectNotFoundException.class).when(this.bandeiraService).delete(1L);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(TipoEndPoint.bandeiraComId, 1L)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// Metodos
	private String jsonParse(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}
}
