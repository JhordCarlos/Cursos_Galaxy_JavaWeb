package com.proyecto.centros.mtc.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DistritoDto {

	private String id;
	private String nombre;
	private String provincia_id;
	private String provincia_nombre;
	private String departamento_id;
	private String departamento_nombre;
	
}
