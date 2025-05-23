package com.proyecto.centros.mtc.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.centros.mtc.app.entity.DepartamentoEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;
import com.proyecto.centros.mtc.app.repository.DepartamentoRepository;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

	private final DepartamentoRepository departamentoRepository;
	
	public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository) {
		this.departamentoRepository = departamentoRepository;
	}


	@Override
	public List<DepartamentoEntity> findAll() throws ServiceException {
		try {
			return this.departamentoRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
