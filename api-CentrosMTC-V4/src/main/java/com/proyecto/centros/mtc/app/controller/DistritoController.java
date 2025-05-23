package com.proyecto.centros.mtc.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.centros.mtc.app.dto.DistritoDto;
import com.proyecto.centros.mtc.app.entity.DistritoEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;
import com.proyecto.centros.mtc.app.repository.DistritoRepository;
import com.proyecto.centros.mtc.app.services.DistritoService;
import com.proyecto.centros.mtc.app.util.MensajesError;

@RestController
@RequestMapping("/api/v1/distrito")
public class DistritoController {

    	private final DistritoService distritoService;
	
	private Map<String, String> map = new HashMap<>();
	
	public DistritoController(DistritoService distritoService, DistritoRepository distritoRepository) {
		this.distritoService = distritoService;
	}

	@GetMapping("/by-provincia")
	public ResponseEntity<?> getAllByProvincia(@RequestParam String id) {
		map.clear();
		try {
			List<DistritoDto> lstDistritoDto = this.distritoService.findAllDistritos(id);
			if (lstDistritoDto.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstDistritoDto);
			}
		} catch (ServiceException e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
		
	}

}
