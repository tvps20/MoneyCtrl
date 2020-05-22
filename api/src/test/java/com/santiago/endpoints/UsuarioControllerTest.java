package com.santiago.endpoints;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.santiago.builders.UsuarioBuilder;
import com.santiago.domain.Usuario;
import com.santiago.endpoints.enuns.TipoEndPoint;
import com.santiago.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private UsuarioService service;

	private List<Usuario> entityList;

	@Before
	public void setup() {
		this.entityList = UsuarioBuilder.mockCollectionUsuariosBuilder().getUsuarios();
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

	public String getRoute() {
		return TipoEndPoint.USUARIO;
	}
}
