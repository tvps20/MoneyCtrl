package com.santiago.moneyctrl.endpoints;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.moneyctrl.dtos.NewPasswordDTO;
import com.santiago.moneyctrl.security.JWTUtil;
import com.santiago.moneyctrl.security.UserSS;
import com.santiago.moneyctrl.services.AuthService;
import com.santiago.moneyctrl.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthService authService;

	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		log.info("[POST REFRESH TOKEN] - Renovando token.");
		UserSS user = UsuarioService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		
		log.info("[POST REFRESH TOKEN] - Token renovado com sucesso.");
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody NewPasswordDTO objDto){
		log.info("[POST FORGOT] - Atualizando nova senha.");
		this.authService.changePassword(objDto);
		
		log.info("[POST FORGOT] - Atualizado com sucesso.");
		return ResponseEntity.noContent().build();
	}
}
