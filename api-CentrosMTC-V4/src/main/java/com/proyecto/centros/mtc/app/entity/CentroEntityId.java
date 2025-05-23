package com.proyecto.centros.mtc.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter 
@Setter
@ToString
@Embeddable
public class CentroEntityId {

	@Column(name = "CENTROS_TIPO",nullable = false, length = 2)
    private int tipo;
	
	@Column(name = "CENTROS_RUC",nullable = false, length = 11)
    private String ruc;
	
}
