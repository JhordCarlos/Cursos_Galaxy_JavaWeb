package com.proyecto.centros.mtc.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.centros.mtc.app.dto.ProvinciaDto;
import com.proyecto.centros.mtc.app.entity.ProvinciaEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;
import com.proyecto.centros.mtc.app.services.ProvinciaService;
import com.proyecto.centros.mtc.app.util.MensajesError;

@RestController
@RequestMapping("/api/v1/provincia")
public class ProvinciaController {

	private final ProvinciaService provinciaService;
	private Map<String, String> map = new HashMap<>();
	
	public ProvinciaController(ProvinciaService provinciaService) {
		this.provinciaService = provinciaService;
	}
	
	
	@GetMapping("/by-departamento")
	public ResponseEntity<?> getAllByDepartamento(@RequestParam String id) {
		map.clear();
		try {
			List<ProvinciaDto> lstProvinciaDto= this.provinciaService.findAllProvincias(id);
			if (lstProvinciaDto.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstProvinciaDto);
			}
		} catch (ServiceException e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
		
	}

}
