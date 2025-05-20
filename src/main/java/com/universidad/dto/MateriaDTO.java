package com.universidad.dto;

import lombok.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO que representa una materia académica.
 * Incluye validaciones básicas para nombre, código y créditos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaDTO implements Serializable {

    /** Identificador único de la materia */
    private Long id;

    /** Nombre de la materia */
    @NotBlank(message = "El nombre de la materia es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombreMateria;

    /** Código único de la materia */
    @NotBlank(message = "El código único es obligatorio")
    @Size(min = 3, max = 20, message = "El código debe tener entre 3 y 20 caracteres")
    private String codigoUnico;

    /** Número de créditos de la materia */
    @NotNull(message = "Los créditos son obligatorios")
    @Min(value = 1, message = "La materia debe tener al menos 1 crédito")
    @Max(value = 10, message = "La materia no puede tener más de 10 créditos")
    private Integer creditos;

    /** Lista de IDs de materias que son prerequisitos */
    private List<Long> prerequisitos;

    /** Lista de IDs de materias para las que esta materia es prerequisito */
    private List<Long> esPrerequisitoDe;
}
