package com.proyecto.centros.mtc.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CentrosRequest {

	@NotBlank(message = "El RUC es obligatorio")
	@Size(min = 11, max = 11, message = "El RUC debe tener exactamente 11 caracteres")
	private String ruc;
	
	@NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no debe exceder los 100 caracteres")
	private String nombre;
	
	@NotBlank(message = "La dirección es obligatoria")
    @Size(max = 300, message = "La dirección no debe exceder los 200 caracteres")
	private String direccion;
	
	@NotBlank(message = "El departamento es obligatorio")
	@Pattern(regexp = "^(?!0{1,2}$).+$", message = "El Departamento no es válido")
	private String departamentoId;
	
	@NotBlank(message = "La provincia es obligatorio")
	@Pattern(regexp = "^(?!0{1,4}$).+$", message = "La provincia no es válido")
	private String provinciaId;
	
	@NotBlank(message = "El distrito es obligatorio")
	@Pattern(regexp = "^(?!0{1,6}$).+$", message = "El distrito no es válido")
	private String distritoId;
	
	@Min(value = 0, message = "Autorización no es válida")
    @Max(value = 1, message = "Autorización no es válida")
	private int autorizacion;
}
