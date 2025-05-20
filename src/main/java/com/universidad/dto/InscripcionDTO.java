package com.universidad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO que representa la inscripción de un estudiante a una materia.
 * Incluye validaciones para asegurar referencias válidas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionDTO implements Serializable {

    /** Identificador único de la inscripción */
    private Long id;

    /** ID del estudiante que se inscribe */
    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;

    /** ID de la materia a la que se inscribe el estudiante */
    @NotNull(message = "El ID de la materia es obligatorio")
    private Long materiaId;

    /** Fecha de la inscripción */
    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate fecha;
}
