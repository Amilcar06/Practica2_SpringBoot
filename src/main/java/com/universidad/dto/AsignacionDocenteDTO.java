package com.universidad.dto;

import lombok.*;

import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO para la asignación de un docente a una materia específica.
 * Incluye validaciones para garantizar integridad de los datos.
 *
 * @author Universidad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionDocenteDTO implements Serializable {

    /** Identificador único de la asignación */
    private Long id;

    /** ID del docente asignado */
    @NotNull(message = "El ID del docente es obligatorio")
    private Long docenteId;

    /** ID de la materia asignada */
    @NotNull(message = "El ID de la materia es obligatorio")
    private Long materiaId;

    /** Gestión académica (ej. 2025-1) */
    @NotBlank(message = "La gestión es obligatoria")
    @Pattern(regexp = "^[0-9]{4}-[1-2]$", message = "La gestión debe tener formato 'YYYY-1' o 'YYYY-2'")
    private String gestion;

    /** Paralelo de la materia (ej. A, B, C) */
    @NotBlank(message = "El paralelo es obligatorio")
    @Size(min = 1, max = 2, message = "El paralelo debe tener entre 1 y 2 caracteres")
    @Pattern(regexp = "^[A-Z]$", message = "El paralelo debe ser una letra mayúscula (A-Z)")
    private String paralelo;
}
