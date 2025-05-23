package com.proyecto.centros.mtc.app.services;

import java.util.List;

import com.proyecto.centros.mtc.app.dto.ProvinciaDto;
import com.proyecto.centros.mtc.app.entity.ProvinciaEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;

public interface ProvinciaService {

	
	List<ProvinciaDto> findAllProvincias(String departamentoId) throws ServiceException; 
}
