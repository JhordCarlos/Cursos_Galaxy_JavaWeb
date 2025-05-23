package com.proyecto.centros.mtc.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.centros.mtc.app.entity.DepartamentoEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;
import com.proyecto.centros.mtc.app.services.DepartamentoService;
import com.proyecto.centros.mtc.app.util.MensajesError;

@RestController
@RequestMapping("/api/v1/departamento")
public class DepartamentoController {

	private Map<String, String> map = new HashMap<>();

	private final DepartamentoService departamentoService;

	public DepartamentoController(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		try {
			List<DepartamentoEntity> lstDepartamentoEntity = this.departamentoService.findAll();
			if (lstDepartamentoEntity.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstDepartamentoEntity);
			}
		} catch (ServiceException e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}
}
