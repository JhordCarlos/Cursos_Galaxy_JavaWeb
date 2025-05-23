package com.proyecto.centros.mtc.app.mapper;

import com.proyecto.centros.mtc.app.dto.CentrosDto;
import com.proyecto.centros.mtc.app.dto.CentrosRequest;
import com.proyecto.centros.mtc.app.entity.CentroEntityId;
import com.proyecto.centros.mtc.app.entity.CentrosEntity;
import com.proyecto.centros.mtc.app.entity.DepartamentoEntity;
import com.proyecto.centros.mtc.app.entity.DistritoEntity;
import com.proyecto.centros.mtc.app.entity.ProvinciaEntity;

import static com.proyecto.centros.mtc.app.constantes.ConstantesGlobales.CENTRO_CON_AUTORIZACION;
import static com.proyecto.centros.mtc.app.constantes.ConstantesGlobales.CENTRO_SIN_AUTORIZACION;

public class CentrosMapper {

	public static CentrosDto centroEntityToDto(CentrosEntity centroEntity) {
		CentrosDto centrosDto = new CentrosDto();
		centrosDto.setRuc(centroEntity.getId().getRuc());
		centrosDto.setNombre(centroEntity.getNombres());
		centrosDto.setDireccion(centroEntity.getDireccion());
		centrosDto.setDepartamentoId(centroEntity.getDepartamento().getId());
		centrosDto.setNombreDepartamento(centroEntity.getDepartamento().getNombre().toUpperCase());
		centrosDto.setProvinciaId(centroEntity.getProvincia().getId());
		centrosDto.setNombreProvincia(centroEntity.getProvincia().getNombre().toUpperCase());
		centrosDto.setDistritoId(centroEntity.getDistrito().getId());
		centrosDto.setNombreDistrito(centroEntity.getDistrito().getNombre().toUpperCase());
		centrosDto.setAutorizacionId(centroEntity.getAutorizacion());
		centrosDto.setAutorizacion((centroEntity.getAutorizacion() == 1 ? CENTRO_CON_AUTORIZACION : CENTRO_SIN_AUTORIZACION));
		return centrosDto;
	}
	
	public static CentrosEntity centroRequestToEntity(CentrosRequest centrosRequest) {
		CentrosEntity centrosEntity = new CentrosEntity();
		CentroEntityId id  = new CentroEntityId();
		id.setRuc(centrosRequest.getRuc());
		centrosEntity.setId(id);
		centrosEntity.setNombres(centrosRequest.getNombre());
		centrosEntity.setDireccion(centrosRequest.getDireccion());
		centrosEntity.setDepartamento(new DepartamentoEntity(centrosRequest.getDepartamentoId(),""));
		centrosEntity.setProvincia(new ProvinciaEntity(centrosRequest.getProvinciaId(),"",centrosRequest.getDepartamentoId()));
		centrosEntity.setDistrito(new DistritoEntity(centrosRequest.getDistritoId(),"", centrosRequest.getProvinciaId(),centrosRequest.getDepartamentoId()));
		centrosEntity.setAutorizacion(centrosRequest.getAutorizacion());
		centrosEntity.setUbigeo(centrosRequest.getDistritoId());
		return centrosEntity;
	}
	
}
