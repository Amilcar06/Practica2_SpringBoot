package com.universidad.validation;

import com.universidad.dto.MateriaDTO;
import com.universidad.repository.MateriaRepository;
import org.springframework.stereotype.Component;

@Component
public class MateriaValidator {

    private final MateriaRepository materiaRepository;

    public MateriaValidator(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public void validaNombreMateria(String nombreMateria) {
        if (nombreMateria == null || nombreMateria.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la materia no puede estar vacío o nulo.");
        }
    }

    public void validaCodigoUnico(String codigoUnico) {
        if (codigoUnico == null || codigoUnico.trim().isEmpty()) {
            throw new IllegalArgumentException("El código único no puede estar vacío o nulo.");
        }

        if (materiaRepository.existsByCodigoUnico(codigoUnico)) {
            throw new IllegalArgumentException("Ya existe una materia con este código único.");
        }
    }

    public void validaCreditos(Integer creditos) {
        if (creditos == null || creditos < 1 || creditos > 10) {
            throw new IllegalArgumentException("Los créditos deben estar entre 1 y 10.");
        }
    }

    public void validacionCompletaMateria(MateriaDTO materiaDTO) {
        validaNombreMateria(materiaDTO.getNombreMateria());
        validaCodigoUnico(materiaDTO.getCodigoUnico());
        validaCreditos(materiaDTO.getCreditos());
        // Podrías validar también prerequisitos si lo necesitas
    }

    // Opción adicional si quieres definir tu propia excepción de negocio
    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}
