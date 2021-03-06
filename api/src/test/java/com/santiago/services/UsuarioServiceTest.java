package com.santiago.services;

import java.util.List;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.builders.UsuarioBuilder;
import com.santiago.domain.Usuario;
import com.santiago.dtos.UsuarioDTO;
import com.santiago.repositories.UsuarioRepository;
import com.santiago.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest extends BaseServiceTest<Usuario, UsuarioDTO> {

	@InjectMocks
	private UsuarioService usuarioService;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario mockEntityBuilder() {
		return UsuarioBuilder.mockUsuarioBuilder().getUsuario();
	}

	@Override
	public Optional<Usuario> mockEntityOptBuilder() {
		return UsuarioBuilder.mockUsuarioBuilder().getUsuarioOpt();
	}

	@Override
	public List<Usuario> mockCollectionEntityListBuilder() {
		return UsuarioBuilder.mockCollectionUsuariosBuilder().getUsuarios();
	}

	@Override
	public IServiceCrud<Usuario, UsuarioDTO> getService() {
		return this.usuarioService;
	}

	@Override
	public JpaRepository<Usuario, Long> getRepository() {
		return this.usuarioRepository;
	}

	@Override
	public Usuario getEntityUpdate() {
		Usuario usuarioUp = this.entity;
		usuarioUp.setNome("Usuario Atualizado");
		return usuarioUp;
	}
}
