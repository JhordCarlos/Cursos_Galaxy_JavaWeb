package com.proyecto.centros.mtc.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.centros.mtc.app.dto.DistritoDto;
import com.proyecto.centros.mtc.app.entity.DistritoEntity;
import com.proyecto.centros.mtc.app.exception.ServiceException;
import com.proyecto.centros.mtc.app.repository.DistritoRepository;

@Service
public class DistritoServiceImpl implements DistritoService {

	private final DistritoRepository distritoRepository;
	
	public DistritoServiceImpl(DistritoRepository distritoRepository) {
		this.distritoRepository = distritoRepository;
	}
	
	@Override
	public List<DistritoDto> findAllDistritos(String provinciaId) throws ServiceException {
		try {
			return this.distritoRepository.listaPorProvincia(provinciaId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
