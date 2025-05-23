package com.proyecto.centros.mtc.app.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.centros.mtc.app.dto.CentrosDto;
import com.proyecto.centros.mtc.app.entity.CentrosEntity;
import com.proyecto.centros.mtc.app.entity.DepartamentoEntity;
import com.proyecto.centros.mtc.app.entity.DistritoEntity;
import com.proyecto.centros.mtc.app.entity.ProvinciaEntity;
import com.proyecto.centros.mtc.app.exception.CentrosExistsException;
import com.proyecto.centros.mtc.app.exception.ServiceException;
import com.proyecto.centros.mtc.app.mapper.CentrosMapper;
import com.proyecto.centros.mtc.app.repository.CentrosRepository;

import static com.proyecto.centros.mtc.app.constantes.ConstantesGlobales.USUARIO;
import static com.proyecto.centros.mtc.app.constantes.ConstantesGlobales.ESTADO_ACTIVO;;

@Service
public class CentrosServiceImpl implements CentrosService {

	private final CentrosRepository centrosRepository;

	public CentrosServiceImpl(CentrosRepository centrosRepository) {
		this.centrosRepository = centrosRepository;
	}

	@Override
	public List<CentrosDto> findByNombres(int tipo, String nombre) {
		List<CentrosDto> lstCentrosDto = new ArrayList<>();
		try {
			nombre = "%" + nombre.trim() + "%";
			List<CentrosEntity> lstCentrosEntity = centrosRepository.findByTipoAndNombre(tipo, nombre);
			lstCentrosEntity.stream().forEach(centro -> lstCentrosDto.add(CentrosMapper.centroEntityToDto(centro)));
			return lstCentrosDto;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CentrosDto findByRuc(int tipo, String ruc) {
		try {
			Optional<CentrosEntity> ce = centrosRepository.findByIdTipoAndIdRucAndEstado(tipo, ruc, 1);
			if (!ce.isPresent()) {
				return null;
			}
			return CentrosMapper.centroEntityToDto(ce.get());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<CentrosDto> findByDepartamento(int tipo, String departamentoId) throws ServiceException {
		try {
			List<CentrosDto> lstCentrosDto = new ArrayList<>();
			List<CentrosEntity> lstCentrosEntity = centrosRepository.findByIdTipoAndDepartamentoIdAndEstado(tipo,
					departamentoId, 1);
			lstCentrosEntity.stream().forEach(centro -> lstCentrosDto.add(CentrosMapper.centroEntityToDto(centro)));
			return lstCentrosDto;
		} catch (Exception e) {
			System.err.println(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CentrosDto> findByProvincia(int tipo, String provinciaId) throws ServiceException {
		try {
			List<CentrosDto> lstCentrosDto = new ArrayList<>();
			List<CentrosEntity> lstCentrosEntity = centrosRepository.findByIdTipoAndProvinciaIdAndEstado(tipo,
					provinciaId, 1);
			lstCentrosEntity.stream().forEach(centro -> lstCentrosDto.add(CentrosMapper.centroEntityToDto(centro)));
			return lstCentrosDto;
		} catch (Exception e) {
			System.err.println(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CentrosDto> findByDistrito(int tipo, String distritoId) throws ServiceException {
		try {
			List<CentrosDto> lstCentrosDto = new ArrayList<>();
			List<CentrosEntity> lstCentrosEntity = centrosRepository.findByIdTipoAndDistritoIdAndEstado(tipo,
					distritoId, 1);
			lstCentrosEntity.stream().forEach(centro -> lstCentrosDto.add(CentrosMapper.centroEntityToDto(centro)));
			return lstCentrosDto;
		} catch (Exception e) {
			System.err.println(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean save(int tipo, CentrosEntity centroEntity) throws CentrosExistsException, ServiceException {
		try {
			String ruc = centroEntity.getId().getRuc();
			Optional<CentrosEntity> optCentroEntity = centrosRepository.findByIdTipoAndIdRuc(tipo, ruc);

			if (optCentroEntity.isEmpty()) {
				LocalDateTime fechaActual = LocalDateTime.now();
				centroEntity.getId().setTipo(tipo);
				centroEntity.setEstado(1);
				centroEntity.setFechaRegistro(fechaActual);
				centroEntity.setUsuarioRegistro(USUARIO);
				this.centrosRepository.save(centroEntity);
			} else {
				if (optCentroEntity.get().getEstado() == ESTADO_ACTIVO) {
					throw new CentrosExistsException("RUC a registrar ya se encuentra registado");
				}
				update(tipo, ruc, 0, centroEntity);
			}
			return true;
		} catch (CentrosExistsException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public boolean update(int tipo, String ruc, int estado, CentrosEntity centroEntity)
			throws CentrosExistsException, ServiceException {
		try {

			Optional<CentrosEntity> optCentroEntity = centrosRepository.findByIdTipoAndIdRucAndEstado(tipo, ruc,
					estado);

			if (optCentroEntity.isPresent()) {

				CentrosEntity oCentroEntity = optCentroEntity.get();
				oCentroEntity.getId().setTipo(tipo);
				oCentroEntity.getId().setRuc(ruc);
				oCentroEntity.setNombres(centroEntity.getNombres());
				oCentroEntity.setDireccion(centroEntity.getDireccion());

				DepartamentoEntity departamentoEntity = new DepartamentoEntity(centroEntity.getDepartamento().getId(),
						"");
				oCentroEntity.setDepartamento(departamentoEntity);

				ProvinciaEntity provinciaEntity = new ProvinciaEntity(centroEntity.getProvincia().getId(), "",
						centroEntity.getDepartamento().getId());
				oCentroEntity.setProvincia(provinciaEntity);

				DistritoEntity distritoEntity = new DistritoEntity(centroEntity.getDistrito().getId(), "",
						centroEntity.getProvincia().getId(),centroEntity.getDepartamento().getId());

				oCentroEntity.setDistrito(distritoEntity);
				oCentroEntity.setAutorizacion(centroEntity.getAutorizacion());
				LocalDateTime fechaActual = LocalDateTime.now();
				oCentroEntity.setFechaModificacion(fechaActual);
				oCentroEntity.setUsuarioModificacion(USUARIO);
				oCentroEntity.setEstado(1);
				this.centrosRepository.save(oCentroEntity);
				return true;
			} else {
				throw new CentrosExistsException("Registro a actualizar no se encuentra registrado");
			}

		} catch (CentrosExistsException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(int tipo, String ruc) throws CentrosExistsException {
		try {

			Optional<CentrosEntity> optCentrosEntity = centrosRepository.findByIdTipoAndIdRucAndEstado(tipo, ruc, 1);

			if (optCentrosEntity.isEmpty()) {
				throw new CentrosExistsException(
						String.format("El registro con ruc %s no se encuentra registrado", ruc));
			}
			centrosRepository.updateEstado(tipo, ruc);
		} catch (CentrosExistsException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateAutorizacion(int tipo, String ruc, int autorizacion) throws ServiceException {
		try {

			Optional<CentrosEntity> optCentrosEntity = centrosRepository.findByIdTipoAndIdRucAndEstado(tipo, ruc, 1);

			if (optCentrosEntity.isEmpty()) {
				throw new ServiceException(String.format("No existe centro con el ruc %s", ruc));
			}
			centrosRepository.updateAutorizacion(tipo, ruc, autorizacion);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Page<CentrosDto> findByNombresPaging(Pageable pageable, int tipo, String nombre) throws ServiceException {
		
		try {
			nombre = "%" + nombre.trim() + "%";
			Page<CentrosEntity> lstCentrosEntity  = this.centrosRepository.findByNombresPaging(pageable, tipo, nombre);
			return lstCentrosEntity.map(CentrosMapper::centroEntityToDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Page<CentrosDto> findByDepartamentoPaging(Pageable pageable, int tipo, String departamentoId) throws ServiceException {
		try {
			String val = (departamentoId == null) ? "" : departamentoId.trim();
			Page<CentrosEntity> lstCentrosEntity = this.centrosRepository.findByDepartamentoPaging(pageable,tipo, val);
			return lstCentrosEntity.map(CentrosMapper::centroEntityToDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Page<CentrosDto> findByProvinciaPaging(Pageable pageable,int tipo, String provinciaId) throws ServiceException {
		try {
			String val = (provinciaId == null) ? "" : provinciaId.trim();
			Page<CentrosEntity> lstCentrosEntity = this.centrosRepository.findByProvinciaPaging(pageable,tipo, val);
			return lstCentrosEntity.map(CentrosMapper::centroEntityToDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Page<CentrosDto> findByDistritoPaging(Pageable pageable,int tipo, String distritoId) throws ServiceException {
		try {
			String val = (distritoId == null) ? "" : distritoId.trim();
			Page<CentrosEntity> lstCentrosEntity = this.centrosRepository.findByDistritoPaging(pageable,tipo, val);
			return lstCentrosEntity.map(CentrosMapper::centroEntityToDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
