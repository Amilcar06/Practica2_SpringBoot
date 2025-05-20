package com.universidad.dto;

import lombok.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO que representa los datos de un docente.
 * Incluye validaciones para campos obligatorios como nombre, email y número de empleado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocenteDTO implements Serializable {

    /** Identificador único del docente */
    private Long id;

    /** Nombre del docente */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    /** Apellido del docente */
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    /** Email del docente */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
    private String email;

    /** Fecha de nacimiento del docente */
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    /** Número de empleado del docente */
    @NotBlank(message = "El número de empleado es obligatorio")
    @Size(min = 4, max = 20, message = "El número de empleado debe tener entre 4 y 20 caracteres")
    private String nroEmpleado;

    /** Departamento al que pertenece el docente */
    @NotBlank(message = "El departamento es obligatorio")
    @Size(min = 3, max = 50, message = "El departamento debe tener entre 3 y 50 caracteres")
    private String departamento;
}
