package com.proyecto.centros.mtc.app.dto;

import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author jpant
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Relation(collectionRelation = "data")

public class CentrosDto {
 
    private String ruc;
    private String nombre;
    private String direccion;
    private String departamentoId;
    private String nombreDepartamento;
    private String provinciaId;
    private String nombreProvincia;
    private String distritoId;
    private String nombreDistrito;
    private int autorizacionId;
    private String autorizacion;
}
