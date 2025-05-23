package com.proyecto.centros.mtc.app.services;

import java.util.List;

import com.proyecto.centros.mtc.app.entity.DepartamentoEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;


public interface DepartamentoService {

	List<DepartamentoEntity> findAll() throws ServiceException;
	
}
