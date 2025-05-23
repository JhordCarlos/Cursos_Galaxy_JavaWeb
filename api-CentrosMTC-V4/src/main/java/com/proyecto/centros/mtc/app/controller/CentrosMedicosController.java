package com.proyecto.centros.mtc.app.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Objects.isNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.proyecto.centros.mtc.app.dto.CentrosDto;
import com.proyecto.centros.mtc.app.dto.CentrosRequest;
import com.proyecto.centros.mtc.app.entity.CentrosEntity;
import com.proyecto.centros.mtc.app.exception.CentrosExistsException;
import com.proyecto.centros.mtc.app.exception.ServiceException;
import com.proyecto.centros.mtc.app.mapper.CentrosMapper;
import com.proyecto.centros.mtc.app.services.AutorizadorImpl;
import com.proyecto.centros.mtc.app.services.CentrosService;
import com.proyecto.centros.mtc.app.util.MensajesError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static com.proyecto.centros.mtc.app.constantes.ConstantesGlobales.CENTRO_MEDICO;
import static com.proyecto.centros.mtc.app.constantes.ConstantesGlobales.ESTADO_ACTIVO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/centromedico")
public class CentrosMedicosController {

	private Map<String, String> map = new HashMap<>();
	private final CentrosService centrosService;
	private final AutorizadorImpl autorizador;

	public CentrosMedicosController(CentrosService centrosService, AutorizadorImpl autorizador) {
		this.centrosService = centrosService;
		this.autorizador = autorizador;
	}

	@Tag(name = "get", description = "Métodos GET de Api Centros Médicos MTC")
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		
		List<CentrosDto> lstCentrosMedicos = this.centrosService.findByNombres(CENTRO_MEDICO, "");
		if (lstCentrosMedicos.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(lstCentrosMedicos);
		}
	}
	
	@Tag(name = "get-paginado", description = "Métodos GET de Api Centros Médicos MTC con paginado")
	@GetMapping("/all-pagin")
	public ResponseEntity<?>  getAllPagin(@RequestParam Integer page, @RequestParam Integer size){

		map.clear();
		try {
			Pageable pageable= PageRequest.of(page,size);
			Page<CentrosDto> lstCentroDto = this.centrosService.findByNombresPaging(pageable,CENTRO_MEDICO,"");
			if (lstCentroDto.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstCentroDto);
			}
		} catch (Exception e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@Tag(name = "get-hateoas", description = "Métodos GET de Api Centros Médicos MTC con Hateoas")
	@GetMapping("/all-hateoas")
	public ResponseEntity<CollectionModel<EntityModel<CentrosDto>>> getAllHateoas(
			@RequestHeader("authorization") String authorization) {

		if (!autorizador.validaUsuario(authorization)) {
			CollectionModel<EntityModel<CentrosDto>> forbiddenResponse = CollectionModel.of(Collections.emptyList(),
					Link.of(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/centromedico/all")
							.toUriString(), "self"));
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);
		}

		List<EntityModel<CentrosDto>> lstcentrosDto = centrosService.findByNombres(CENTRO_MEDICO, "").stream()
				.map(centro -> EntityModel.of(centro,
						linkTo(methodOn(CentrosMedicosController.class).getByRuc_hateoas(centro.getRuc(), "")).withSelfRel(),
						linkTo(methodOn(CentrosMedicosController.class).getAllHateoas("")).withRel("centrosmedicos")))
				.collect(Collectors.toList());

		CollectionModel<EntityModel<CentrosDto>> response = CollectionModel.of(lstcentrosDto,
				linkTo(methodOn(CentrosMedicosController.class).getAllHateoas("")).withSelfRel());
		return ResponseEntity.ok(response);
	}
	
	@Tag(name = "get")
	@GetMapping("/by-ruc")
	public ResponseEntity<?> getByRuc(@RequestParam String ruc,@RequestHeader("authorization") String authorization) {

		map.clear();

		if (!autorizador.validaUsuario(authorization)) {
			map.put("error", "El usuario no está autorizado para esta operación");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
		} 

		CentrosDto centro = centrosService.findByRuc(CENTRO_MEDICO, ruc);
		EntityModel<?> response = null;

		if (isNull(centro)) {
			map.put("error", "No se encuentra el registro");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(centro);
	}
	
	@Tag(name = "get-hateoas")
	@GetMapping("/by-ruc-hateoas")
	public ResponseEntity<EntityModel<?>> getByRuc_hateoas(@RequestParam String ruc,
			@RequestHeader("authorization") String authorization) {

		map.clear();

		if (!autorizador.validaUsuario(authorization)) {

			EntityModel<?> forbiddenResponse = EntityModel.of(map,
					linkTo(methodOn(CentrosMedicosController.class).getByRuc_hateoas(ruc, "")).withSelfRel(),
					linkTo(methodOn(CentrosMedicosController.class).getAllHateoas("")).withRel("centrosmedicos"));
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);
		}

		CentrosDto centro = centrosService.findByRuc(CENTRO_MEDICO, ruc);
		EntityModel<?> response = null;

		if (isNull(centro)) {
			map.put("error", "No se encuentra el registro");
			response = EntityModel.of(map,
					linkTo(methodOn(CentrosMedicosController.class).getByRuc_hateoas(ruc, "")).withSelfRel(),
					linkTo(methodOn(CentrosMedicosController.class).getAllHateoas("")).withRel("centrosmedicos"));
			return ResponseEntity.ok(response);
		}

		response = EntityModel.of(centro,
				linkTo(methodOn(CentrosMedicosController.class).getByRuc_hateoas(ruc, "")).withSelfRel(),
				linkTo(methodOn(CentrosMedicosController.class).getAllHateoas("")).withRel("centrosmedicos"));
		return ResponseEntity.ok(response);
	}

	@Tag(name = "get")
	@GetMapping("/by-nombre")
	public ResponseEntity<?> getByNombres(@RequestParam String nombre) {

		List<CentrosDto> lstCentrosMedicos = this.centrosService.findByNombres(CENTRO_MEDICO, nombre);
		if (lstCentrosMedicos.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(lstCentrosMedicos);
		}

	}

	@Tag(name = "get")
	@GetMapping("/by-departamento")
	public ResponseEntity<?> getByDepartamento(@RequestParam String id) {
		try {
			List<CentrosDto> lstCentrosMedicos = this.centrosService.findByDepartamento(CENTRO_MEDICO, id);
			if (lstCentrosMedicos.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstCentrosMedicos);
			}
		} catch (ServiceException e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@Tag(name = "get-paginado", description = "Métodos GET de Api Centros Médicos MTC con paginado")
	@GetMapping("/by-departamento-pagin")
	public ResponseEntity<?>  getByDepartamentoPagin(@RequestParam String id, @RequestParam Integer page, @RequestParam Integer size){

		map.clear();
		try {
			Pageable pageable= PageRequest.of(page,size);
			Page<CentrosDto> lstCentroDto = this.centrosService.findByDepartamentoPaging(pageable,CENTRO_MEDICO,id);
			if (lstCentroDto.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstCentroDto);
			}
		} catch (Exception e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}

	@Tag(name = "get")
	@GetMapping("/by-provincia")
	public ResponseEntity<?> getByProvincia(@RequestParam String id) {
		try {
			List<CentrosDto> lstCentrosMedicos = this.centrosService.findByProvincia(CENTRO_MEDICO, id);
			if (lstCentrosMedicos.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstCentrosMedicos);
			}
		} catch (ServiceException e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@Tag(name = "get-paginado")
	@GetMapping("/by-provincia-pagin")
	public ResponseEntity<?> getByProvinciaPagin(@RequestParam String id, @RequestParam Integer page, @RequestParam Integer size) {
		map.clear();
		try {
			Pageable pageable= PageRequest.of(page,size);
			Page<CentrosDto> lstCentroDto = this.centrosService.findByProvinciaPaging(pageable,CENTRO_MEDICO,id);
			if (lstCentroDto.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstCentroDto);
			}
		} catch (Exception e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}

	@Tag(name = "get")
	@GetMapping("/by-distrito")
	public ResponseEntity<?> getByDistrito(@RequestParam String id) {
		try {
			List<CentrosDto> lstCentrosMedicos = this.centrosService.findByDistrito(CENTRO_MEDICO, id);
			if (lstCentrosMedicos.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstCentrosMedicos);
			}
		} catch (ServiceException e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@Tag(name = "get-paginado")
	@GetMapping("/by-distrito-pagin")
	public ResponseEntity<?> getByDistritoPagin(@RequestParam String id, @RequestParam Integer page, @RequestParam Integer size) {
		map.clear();
		try {
			Pageable pageable= PageRequest.of(page,size);
			Page<CentrosDto> lstCentroDto = this.centrosService.findByDistritoPaging(pageable,CENTRO_MEDICO,id);
			if (lstCentroDto.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(lstCentroDto);
			}
		} catch (Exception e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}

	
	@Operation(summary = "Registra un centro médico del MTC",
            description = "Registra un centro médico del MTC")
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody CentrosRequest centrosRequest, @RequestHeader("authorization") String authorization) {
		
		try {

			map.clear();
			if (!autorizador.validaUsuario(authorization)) {
				map.put("error", "El usuario no está autorizado para esta operación");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
			}

			CentrosEntity oCentrosEntity = CentrosMapper.centroRequestToEntity(centrosRequest);
			if (!this.centrosService.save(CENTRO_MEDICO, oCentrosEntity)) {
				map.put("alerta", MensajesError.MSG_BAD_REQUEST);
				return ResponseEntity.badRequest().body(map);
			}
			return new ResponseEntity<>(HttpStatus.CREATED);

		} catch (ServiceException e) {
			map.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(map);
		} catch (CentrosExistsException e) {
			map.put("alerta", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}

	}

	@Operation(summary = "Actualiza un centro médico del MTC",
            description = "Actualiza un centro médico del MTC")
	@PutMapping("/{ruc}")
	public ResponseEntity<?> update(@PathVariable String ruc, @RequestBody CentrosRequest centroRequest,
			@RequestHeader("authorization") String authorization
			) {

		try {
			map.clear();
			if (!autorizador.validaUsuario(authorization)) {
				map.put("error", "El usuario no está autorizado para esta operación");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
			}

			CentrosEntity centroEntity = CentrosMapper.centroRequestToEntity(centroRequest);
			if (!this.centrosService.update(CENTRO_MEDICO, ruc, ESTADO_ACTIVO, centroEntity)) {
				map.put("alerta", MensajesError.MSG_BAD_REQUEST);
				return ResponseEntity.badRequest().body(map);
			} else {
				return ResponseEntity.ok().build();
			}
		} catch (CentrosExistsException e) {
			map.put("alerta", e.getMessage());
			return ResponseEntity.badRequest().body(map);

		} catch (ServiceException e) {
			map.put("error", e.getMessage());
			return ResponseEntity.internalServerError().body(map);
		}
	}

	@Operation(summary = "Actualiza autorización de un centro médico del MTC",
            description = "Actualiza autorización de un centro médico del MTC")
	@PatchMapping("/{ruc}")
	public ResponseEntity<?> updateAutorizacion(@PathVariable String ruc, @RequestBody CentrosRequest centroRequest,
			@RequestHeader("authorization") String authorization
			) {
		
		try {
			map.clear();
			if (!autorizador.validaUsuario(authorization)) {
				map.put("error", "El usuario no está autorizado para esta operación");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
			}

			this.centrosService.updateAutorizacion(CENTRO_MEDICO, ruc, centroRequest.getAutorizacion());
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			map.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		} catch (Exception e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}

	@Operation(summary = "Elimina un centro médico del MTC",
            description = "Elimina un centro médico del MTC por RUC")
	@DeleteMapping("/{ruc}")
	public ResponseEntity<?> delete(@PathVariable String ruc, @RequestHeader("authorization") String authorization
			) {

		try {
			map.clear();
			if (!autorizador.validaUsuario(authorization)) {
				map.put("error", "El usuario no está autorizado para esta operación");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
			}

			this.centrosService.delete(CENTRO_MEDICO, ruc);
			return ResponseEntity.ok().build();
		} catch (CentrosExistsException e) {
			map.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		} catch (Exception e) {
			map.put("error", MensajesError.MSG_INTERNAL_ERROR);
			return ResponseEntity.internalServerError().body(map);
		}
	}

}
