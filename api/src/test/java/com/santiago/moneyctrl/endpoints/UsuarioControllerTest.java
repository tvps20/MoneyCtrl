package com.santiago.moneyctrl.endpoints;

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

import com.santiago.moneyctrl.builders.UsuarioBuilder;
import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.domain.enuns.TipoRoles;
import com.santiago.moneyctrl.endpoints.UsuarioController;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private UsuarioService service;

	private Usuario user;
	private List<Usuario> userList;

	@Before
	public void setup() {
		this.user = UsuarioBuilder.mockUsuarioBuilder().getUsuario();
		this.userList = UsuarioBuilder.mockCollectionUsuariosBuilder().getUsuarios();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllEntities() throws Exception {
		when(this.service.findAll()).thenReturn(this.userList);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(this.getRoute()).accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPerfis() throws Exception {
		when(this.service.findById(1L)).thenReturn(this.user);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(this.getRoute() + TipoEndPoint.USUARIO_ID + TipoEndPoint.PERFIl, 1L)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(TipoRoles.USUARIO.toString()));
	}

	public String getRoute() {
		return TipoEndPoint.USUARIO;
	}
}
