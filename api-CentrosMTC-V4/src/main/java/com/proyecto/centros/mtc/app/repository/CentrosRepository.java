package com.proyecto.centros.mtc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.centros.mtc.app.dto.CentrosDto;
import com.proyecto.centros.mtc.app.entity.CentroEntityId;
import com.proyecto.centros.mtc.app.entity.CentrosEntity;

import jakarta.transaction.Transactional;

@Repository
public interface CentrosRepository extends JpaRepository<CentrosEntity, CentroEntityId> {

	@Query(value = "SELECT " + "z.centros_ruc RUC, z.centros_nombres NOMBRE, z.centros_direccion DIRECCION, "
			+ "upper(c.departamento_nombre) AS DEPARTAMENTO, upper(b.provincia_nombre) PROVINCIA, upper(a.distrito_nombre) DISTRITO "
			+ "FROM CENTROS_MTC z " + "inner join DISTRITO a on a.distrito_id = z.centros_ubigeo "
			+ "inner join PROVINCIA b on b.provincia_id = a.provincia_id "
			+ "inner join DEPARTAMENTO c on c.departamento_id = b.departamento_id "
			+ "WHERE z.centros_estado=1 and z.centros_tipo = :tipo and upper(z.centros_nombres) like :nombre "
			+" order by c.departamento_nombre,b.provincia_nombre,a.distrito_nombre", nativeQuery = true)
	List<CentrosDto> findByTipoAndNombreSql(Integer tipo, String nombre);

	@Query(value = "SELECT c from CentrosEntity c where c.id.tipo = :tipo and upper(c.nombres) like :nombre and c.estado = 1 order by c.departamento.nombre, c.provincia.nombre, c.distrito.nombre")
	List<CentrosEntity> findByTipoAndNombre(Integer tipo, String nombre);

	List<CentrosEntity> findByIdTipoAndDepartamentoIdAndEstado(int id, String departamentoId, int estado);

	List<CentrosEntity> findByIdTipoAndProvinciaIdAndEstado(int id, String provinciaId, int estado);

	List<CentrosEntity> findByIdTipoAndDistritoIdAndEstado(int id, String distritoId, int estado);

	Optional<CentrosEntity> findByIdTipoAndIdRucAndEstado(int tipo, String ruc, int estado);
	
	Optional<CentrosEntity> findByIdTipoAndIdRuc(int tipo, String ruc);
	
	@Query(value = "select c from CentrosEntity c where c.id.tipo = :tipo and upper(c.nombres) like :nombre  and c.estado=1" ) 
	Page<CentrosEntity> findByNombresPaging(Pageable pageable,@Param("tipo") int tipo, @Param("nombre") String nombre);
	
	@Query(value = "select c from CentrosEntity c where c.id.tipo = :tipo  and c.departamento.id = :departamentoId and c.estado=1" ) 
	Page<CentrosEntity> findByDepartamentoPaging(Pageable pageable,@Param("tipo") int tipo, @Param("departamentoId") String departamentoId);

	@Query(value = "select c from CentrosEntity c where c.id.tipo = :tipo  and c.provincia.id = :provinciaId and c.estado=1" ) 
	Page<CentrosEntity> findByProvinciaPaging(Pageable pageable,@Param("tipo") int tipo, @Param("provinciaId") String provinciaId);

	@Query(value = "select c from CentrosEntity c where c.id.tipo = :tipo  and c.distrito.id = :distritoId and c.estado=1" ) 
	Page<CentrosEntity> findByDistritoPaging(Pageable pageable,@Param("tipo") int tipo, @Param("distritoId") String distritoId);
	
	@Transactional
	@Modifying
	@Query(value = "update CENTROS_MTC set centros_estado=0 where centros_tipo=:tipo and centros_ruc = :ruc", nativeQuery = true) // SQL(Structured Query Language)
	void updateEstado(@Param("tipo") int tipo, @Param("ruc") String ruc);
	
	@Transactional
	@Modifying
	@Query(value = "update centros_mtc set centros_autorizacion=:autorizacion where centros_tipo=:tipo and centros_ruc = :ruc", nativeQuery = true) // SQL(Structured Query Language)
	void updateAutorizacion(@Param("tipo") int tipo, @Param("ruc") String ruc, @Param("autorizacion") int autorizacion);

}
