package com.universidad.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO para representar una evaluación de un docente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluacionDocenteDTO implements Serializable {

    private Long id;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long docenteId;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private Integer puntuacion;

    @NotBlank(message = "El comentario es obligatorio")
    @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
    private String comentario;

    @NotNull(message = "La fecha de evaluación es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fecha;
}
