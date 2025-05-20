package com.universidad.service.impl;

import com.universidad.dto.AsignacionDocenteDTO;
import com.universidad.model.AsignacionDocente;
import com.universidad.model.Docente;
import com.universidad.model.Materia;
import com.universidad.repository.AsignacionDocenteRepository;
import com.universidad.repository.DocenteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IAsignacionDocenteService;

import com.universidad.validation.AsignacionDocenteValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionDocenteServiceImpl implements IAsignacionDocenteService {

    @Autowired
    private AsignacionDocenteRepository asignacionRepo;
    @Autowired
    private DocenteRepository docenteRepo;
    @Autowired
    private MateriaRepository materiaRepo;
    @Autowired
    private AsignacionDocenteValidator asignacionValidator;

    private AsignacionDocenteDTO mapToDTO(AsignacionDocente asignacion) {
        return AsignacionDocenteDTO.builder()
                .id(asignacion.getId())
                .docenteId(asignacion.getDocente().getId())
                .materiaId(asignacion.getMateria().getId())
                .gestion(asignacion.getGestion())
                .paralelo(asignacion.getParalelo())
                .build();
    }
    @Transactional
    @Override
    public AsignacionDocenteDTO asignarDocente(AsignacionDocenteDTO dto) {
        asignacionValidator.validarAsignacion(dto);  // <-- Validación antes de continuar
        Docente docente = docenteRepo.findById(dto.getDocenteId())
                .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));
        Materia materia = materiaRepo.findById(dto.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));

        AsignacionDocente asignacion = AsignacionDocente.builder()
                .docente(docente)
                .materia(materia)
                .gestion(dto.getGestion())
                .paralelo(dto.getParalelo())
                .build();
        return mapToDTO(asignacionRepo.save(asignacion));
    }
    @Override
    public List<AsignacionDocenteDTO> obtenerAsignacionesPorDocente(Long docenteId) {
        return asignacionRepo.findByDocenteId(docenteId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    @Override
    public List<AsignacionDocenteDTO> obtenerAsignacionesPorMateria(Long materiaId) {
        return asignacionRepo.findByMateriaId(materiaId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public void eliminarAsignacion(Long id) {
        asignacionRepo.deleteById(id);
    }

    @Transactional
    @Override
    public AsignacionDocenteDTO actualizarAsignacion(Long id, AsignacionDocenteDTO dto) {
        asignacionValidator.validarAsignacion(dto);

        AsignacionDocente asignacionExistente = asignacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada con ID: " + id));
        Docente docente = docenteRepo.findById(dto.getDocenteId())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        Materia materia = materiaRepo.findById(dto.getMateriaId())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        asignacionExistente.setDocente(docente);
        asignacionExistente.setMateria(materia);
        asignacionExistente.setGestion(dto.getGestion());
        asignacionExistente.setParalelo(dto.getParalelo());

        return mapToDTO(asignacionRepo.save(asignacionExistente));
    }

    @Override
    public List<AsignacionDocenteDTO> obtenerTodas() {
        return asignacionRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

}
