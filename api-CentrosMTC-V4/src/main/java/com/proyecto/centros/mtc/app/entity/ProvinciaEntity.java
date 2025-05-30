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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jpant
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROVINCIA") // DB
@Entity(name = "Provincia") //Java
public class ProvinciaEntity {
    
	@Id
	@Column(name = "PROVINCIA_ID",nullable = false, length = 4)
    private String id;
	
	@Column(name = "PROVINCIA_NOMBRE",nullable = false, length = 100)
    private String nombre;
	
	@Column(name = "DEPARTAMENTO_ID", nullable = false, length = 2)
    private String departamento_id;
	
}
