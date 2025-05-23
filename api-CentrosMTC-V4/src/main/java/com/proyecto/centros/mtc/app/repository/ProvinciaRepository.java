package com.proyecto.centros.mtc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.centros.mtc.app.dto.ProvinciaDto;
import com.proyecto.centros.mtc.app.entity.ProvinciaEntity;

@Repository
public interface ProvinciaRepository extends JpaRepository<ProvinciaEntity, String>{
	
	@Query(value = "select a.provincia_id, a.provincia_nombre,a.departamento_id,b.departamento_nombre "
			+ "from provincia a "
			+ "inner join departamento b "
			+ "on b.departamento_id = a.departamento_id "
			+ "where a.departamento_id = :departamentoId "
			+ "order by a.provincia_nombre"+ "", nativeQuery = true )
	List<ProvinciaDto> listByDepartamento(String departamentoId);
}
