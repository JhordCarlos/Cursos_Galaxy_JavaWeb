package com.proyecto.centros.mtc.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.centros.mtc.app.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query(value = "select u from UsuarioEntity u where u.usuario =:usuario " +
            " and u.clave =:clave and u.estado=1" )
    Optional<UsuarioEntity> validar(@Param("usuario") String usuario, @Param("clave") String clave);

}
