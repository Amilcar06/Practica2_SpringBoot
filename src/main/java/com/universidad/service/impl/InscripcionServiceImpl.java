package com.universidad.service.impl;

import com.universidad.dto.InscripcionDTO;
import com.universidad.model.Inscripcion;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.repository.InscripcionRepository;
import com.universidad.service.IInscripcionService;
import com.universidad.validation.InscripcionValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements IInscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepo;

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private MateriaRepository materiaRepo;

    @Autowired
    private InscripcionValidator inscripcionValidator;

    /*public InscripcionDTO crear(InscripcionDTO dto) {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudianteRepo.findById(dto.getEstudianteId()).orElseThrow());
        inscripcion.setMateria(materiaRepo.findById(dto.getMateriaId()).orElseThrow());
        inscripcion.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now());
        Inscripcion guardada = inscripcionRepo.save(inscripcion);
        return convertirADTO(guardada);
    }*/
    @Transactional
    @Override
    public InscripcionDTO crear(InscripcionDTO dto) {
        inscripcionValidator.validarDatosInscripcion(dto);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(inscripcionValidator.validarEstudianteExistente(dto.getEstudianteId()));
        inscripcion.setMateria(inscripcionValidator.validarMateriaExistente(dto.getMateriaId()));
        inscripcion.setFecha(dto.getFecha());

        return convertirADTO(inscripcionRepo.save(inscripcion));
    }

    @Transactional
    @Override
    public InscripcionDTO actualizar(Long id, InscripcionDTO dto) {
        Inscripcion inscripcion = inscripcionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscripción no encontrada con id: " + id));

        inscripcionValidator.validarDatosInscripcion(dto);

        inscripcion.setEstudiante(inscripcionValidator.validarEstudianteExistente(dto.getEstudianteId()));
        inscripcion.setMateria(inscripcionValidator.validarMateriaExistente(dto.getMateriaId()));
        inscripcion.setFecha(dto.getFecha());

        return convertirADTO(inscripcionRepo.save(inscripcion));
    }

    @Override
    public void eliminar(Long id) {
        inscripcionRepo.deleteById(id);
    }

    @Override
    public InscripcionDTO obtenerPorId(Long id) {
        return inscripcionRepo.findById(id)
                .map(this::convertirADTO)
                .orElseThrow();
    }

    @Override
    public List<InscripcionDTO> listarTodos() {
        return inscripcionRepo.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private InscripcionDTO convertirADTO(Inscripcion i) {
        InscripcionDTO dto = new InscripcionDTO();
        dto.setId(i.getId());
        dto.setEstudianteId(i.getEstudiante().getId());
        dto.setMateriaId(i.getMateria().getId());
        dto.setFecha(i.getFecha());
        return dto;
    }
}
