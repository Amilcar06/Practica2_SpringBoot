package com.universidad.validation;

import com.universidad.model.Estudiante;
import com.universidad.model.Materia;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.dto.InscripcionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class InscripcionValidator {

    private final EstudianteRepository estudianteRepo;
    private final MateriaRepository materiaRepo;

    public InscripcionValidator(EstudianteRepository estudianteRepo, MateriaRepository materiaRepo) {
        this.estudianteRepo = estudianteRepo;
        this.materiaRepo = materiaRepo;
    }

    public Estudiante validarEstudianteExistente(Long estudianteId) {
        return estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con ID: " + estudianteId));
    }

    public Materia validarMateriaExistente(Long materiaId) {
        return materiaRepo.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("Materia no encontrada con ID: " + materiaId));
    }

    public void validarFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de inscripción no puede ser nula");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inscripción no puede ser futura");
        }
    }

    public void validarDatosInscripcion(InscripcionDTO dto) {
        validarEstudianteExistente(dto.getEstudianteId());
        validarMateriaExistente(dto.getMateriaId());
        validarFecha(dto.getFecha());
    }
}
