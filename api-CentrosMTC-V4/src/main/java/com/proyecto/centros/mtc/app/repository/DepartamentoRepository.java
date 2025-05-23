package com.proyecto.centros.mtc.app.repository;

import org.springframework.stereotype.Repository;
import com.proyecto.centros.mtc.app.entity.DepartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DepartamentoRepository extends JpaRepository<DepartamentoEntity, String>{
	

}
