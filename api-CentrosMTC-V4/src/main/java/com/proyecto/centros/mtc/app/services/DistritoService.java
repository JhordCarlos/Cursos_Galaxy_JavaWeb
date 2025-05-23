package com.proyecto.centros.mtc.app.services;

import java.util.List;

import com.proyecto.centros.mtc.app.dto.DistritoDto;
import com.proyecto.centros.mtc.app.entity.DistritoEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;

public interface DistritoService {

	List<DistritoDto> findAllDistritos(String provinciaId) throws ServiceException;
}
