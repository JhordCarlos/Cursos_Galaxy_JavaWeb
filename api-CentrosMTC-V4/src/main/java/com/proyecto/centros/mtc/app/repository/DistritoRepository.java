package com.proyecto.centros.mtc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.proyecto.centros.mtc.app.dto.DistritoDto;
import com.proyecto.centros.mtc.app.entity.DistritoEntity;

@Repository
public interface DistritoRepository extends JpaRepository<DistritoEntity, String>{

	
	@Query(value = "select a.distrito_id,a.distrito_nombre, b.provincia_id,"
			+ "b.provincia_nombre,b.departamento_id,c.departamento_nombre "
			+ "from distrito a "
			+ "inner join provincia b on "
			+ "b.provincia_id = a.provincia_id "
			+ "inner join departamento c "
			+ "on c.departamento_id = b.departamento_id "
			+ "where a.provincia_id = :provinciaId order by a.distrito_nombre", nativeQuery = true )
	List<DistritoDto> listaPorProvincia(String provinciaId);
}
