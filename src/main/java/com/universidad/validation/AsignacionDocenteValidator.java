package com.universidad.validation;

import com.universidad.dto.AsignacionDocenteDTO;
import org.springframework.stereotype.Component;

@Component
public class AsignacionDocenteValidator {

    public void validarAsignacion(AsignacionDocenteDTO dto) {
        if (dto.getDocenteId() == null) {
            throw new IllegalArgumentException("El docente es obligatorio.");
        }
        if (dto.getMateriaId() == null) {
            throw new IllegalArgumentException("La materia es obligatoria.");
        }
        if (dto.getGestion() == null || dto.getGestion().isBlank()) {
            throw new IllegalArgumentException("La gestión es obligatoria.");
        }
        if (dto.getParalelo() == null || dto.getParalelo().isBlank()) {
            throw new IllegalArgumentException("El paralelo es obligatorio.");
        }
        // Puedes agregar más validaciones aquí según las reglas de negocio
    }
}
