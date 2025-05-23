package com.proyecto.centros.mtc.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.centros.mtc.app.services.Autorizador;

@RestController
@RequestMapping("/api/v1/login")
public class AuthController {

	private Autorizador autorizador;
	private Map<String, String> map = new HashMap<>();
	
	public AuthController(Autorizador autorizador) {
		this.autorizador = autorizador;
	}

	@GetMapping
	public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
		boolean authenticated = autorizador.validaUsuario(authHeader);

		if (authenticated) {
			return ResponseEntity.ok().build();
		} else {
			map.put("error", "Credenciales inválidas");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
		}
	}

}
