/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.centros.mtc.app.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "DEPARTAMENTO") // DB
@Entity(name = "Departamento") //Java

public class DepartamentoEntity {
    
	@Id
	@Column(name = "DEPARTAMENTO_ID",nullable = false, length = 2)
    private String id;
	
	@Column(name = "DEPARTAMENTO_NOMBRE",nullable = false, length = 100)
    private String nombre;

}
