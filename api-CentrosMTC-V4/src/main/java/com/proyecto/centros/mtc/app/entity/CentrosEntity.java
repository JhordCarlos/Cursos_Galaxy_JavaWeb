/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.centros.mtc.app.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "CENTROS_MTC")
@Entity(name = "CentrosEntity")

public class CentrosEntity {

	@EmbeddedId
	private CentroEntityId id;

	@Column(name = "CENTROS_NOMBRES", nullable = false, length = 500)
	private String nombres;

	@ManyToOne
	@JoinColumn(name = "CENTROS_DEPARTAMENTO", nullable = false)
	private DepartamentoEntity departamento;

	@ManyToOne
	@JoinColumn(name = "CENTROS_PROVINCIA", nullable = false)
	private ProvinciaEntity provincia;

	@ManyToOne
	@JoinColumn(name = "CENTROS_DISTRITO", nullable = false)
	private DistritoEntity distrito;

	@Column(name = "CENTROS_UBIGEO", nullable = false, length = 6)
	private String ubigeo;

	@Column(name = "CENTROS_DIRECCION", nullable = false, length = 700)
	private String direccion;

	@Column(name = "CENTROS_AUTORIZACION", nullable = false, length = 1)
	private Integer autorizacion;

	@Column(name = "CENTROS_USUARIO_REGISTRO", nullable = false, length = 20)
	private String usuarioRegistro;

	@Column(name = "CENTROS_FECHA_REGISTRO", nullable = false)
	private LocalDateTime fechaRegistro;

	@Column(name = "CENTROS_USUARIO_MODIFICACION", nullable = true, length = 20)
	private String usuarioModificacion;

	@Column(name = "CENTROS_FECHA_MODIFICACION", nullable = true)
	private LocalDateTime fechaModificacion;
	
	@Column(name = "CENTROS_ESTADO", nullable = false, length = 1)
	private Integer estado;

}
