package com.santiago.moneyctrl.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.santiago.moneyctrl.domain.enuns.TipoRoles;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String login;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	// Construtores
	public UserSS() {
		super();
	}

	public UserSS(Long id, String login, String password, Set<TipoRoles> roles) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.authorities = roles.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
				.collect(Collectors.toList());
	}

	// Gets e Sets
	public Long getId() {
		return this.id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	// Outros Métodos
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	// Outros Métodos
	public boolean hasRole(TipoRoles role) {
		return this.getAuthorities().contains(new SimpleGrantedAuthority(role.getDescricao()));
	}
}
