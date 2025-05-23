package com.proyecto.centros.mtc.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.centros.mtc.app.dto.ProvinciaDto;
import com.proyecto.centros.mtc.app.entity.ProvinciaEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;
import com.proyecto.centros.mtc.app.repository.ProvinciaRepository;

@Service
public class ProvinciaServiceImpl implements ProvinciaService {

	private final ProvinciaRepository provinciaRepository;
	
	public ProvinciaServiceImpl(ProvinciaRepository provinciaRepository) {
		this.provinciaRepository = provinciaRepository;
		
	}
	
	@Override
	public List<ProvinciaDto> findAllProvincias(String departamentoId) throws ServiceException {
		try {
			return this.provinciaRepository.listByDepartamento(departamentoId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


}
