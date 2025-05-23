package com.proyecto.centros.mtc.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO") 
@Entity(name = "UsuarioEntity") 
public class UsuarioEntity {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUsuario")
    @SequenceGenerator(sequenceName = "SEQ_USUARIO", allocationSize = 1, name = "seqUsuario")
    @Column(name = "USUARIO_ID",nullable = false, length = 4)
	private Long id; 
    
	@Column(name = "USUARIO_USER",nullable = false, length = 60, unique = true)
    private String usuario;
	
	@Column(name = "USUARIO_CLAVE",nullable = false, length = 360, unique = true)
    private String clave;
		
	@Column(name = "USUARIO_ESTADO",nullable = false, length = 1)
    private int estado;
    

}
