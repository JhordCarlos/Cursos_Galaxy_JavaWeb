package com.proyecto.centros.mtc.app.services;

import com.proyecto.centros.mtc.app.exception.CentrosExistsException;
import com.proyecto.centros.mtc.app.exception.ServiceException;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.centros.mtc.app.dto.CentrosDto;
import com.proyecto.centros.mtc.app.entity.CentroEntityId;
import com.proyecto.centros.mtc.app.entity.CentrosEntity;

public interface CentrosService {
	
	List<CentrosDto> findByNombres(int tipo, String nombres);
	
	CentrosDto findByRuc(int tipo, String ruc) ;
	
	List<CentrosDto> findByDepartamento(int tipo, String departamentoId) throws ServiceException;
	
	List<CentrosDto> findByProvincia(int tipo, String provinciaId) throws ServiceException;
	
	List<CentrosDto> findByDistrito(int tipo, String distritoId) throws ServiceException;
	
	boolean save(int tipo, CentrosEntity centroEntity) throws  CentrosExistsException, ServiceException;
	
	boolean update(int tipo, String ruc, int estado, CentrosEntity centroEntity) throws CentrosExistsException, ServiceException;
	
	void updateAutorizacion(int tipo, String ruc, int autorizacion) throws ServiceException;
	
	void delete(int tipo, String ruc) throws CentrosExistsException;
	
	Page<CentrosDto> findByNombresPaging(Pageable pageable,int tipo, String nombres) throws ServiceException;
	
	Page<CentrosDto> findByDepartamentoPaging(Pageable pageable,int tipo, String departamentoId) throws ServiceException;
	
	Page<CentrosDto> findByProvinciaPaging(Pageable pageable,int tipo, String provinciaId) throws ServiceException;
	
	Page<CentrosDto> findByDistritoPaging(Pageable pageable,int tipo, String distritoId) throws ServiceException;
	
}
