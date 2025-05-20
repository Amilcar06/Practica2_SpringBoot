package com.universidad.controller;

import com.universidad.dto.EstudianteDTO;
import com.universidad.dto.MateriaDTO;
import com.universidad.model.Estudiante;
import com.universidad.service.IEstudianteService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/estudiantes")
@Validated
@Tag(name = "Estudiantes", description = "API para gestión de estudiantes")
public class EstudianteController {

    private final IEstudianteService estudianteService;
    private static final Logger logger = LoggerFactory.getLogger(EstudianteController.class);

    @Autowired
    public EstudianteController(IEstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Operation(summary = "Obtener todos los estudiantes")
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> obtenerTodosLosEstudiantes() {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodosLosEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @Operation(summary = "Obtener estudiante por número de inscripción")
    @GetMapping("/inscripcion/{numeroInscripcion}")
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorNumeroInscripcion(
            @PathVariable String numeroInscripcion) {
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorNumeroInscripcion(numeroInscripcion);
        return ResponseEntity.ok(estudiante);
    }

    @Operation(summary = "Obtener materias de un estudiante por ID")
    @GetMapping("/{id}/materias")
    public ResponseEntity<List<MateriaDTO>> obtenerMateriasDeEstudiante(@PathVariable("id") Long estudianteId) {
        List<MateriaDTO> materiaDTOs = estudianteService.obtenerMateriasDeEstudiante(estudianteId);
        return ResponseEntity.ok(materiaDTOs);
    }

    @Operation(summary = "Obtener estudiante con bloqueo optimista por ID")
    @GetMapping("/{id}/lock")
    public ResponseEntity<Estudiante> getEstudianteConBloqueo(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.obtenerEstudianteConBloqueo(id);
        return ResponseEntity.ok(estudiante);
    }

    @Operation(summary = "Crear un nuevo estudiante")
    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EstudianteDTO> crearEstudiante(@Valid @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO nuevoEstudiante = estudianteService.crearEstudiante(estudianteDTO);
        return ResponseEntity.status(201).body(nuevoEstudiante);
    }

    @Operation(summary = "Actualizar un estudiante existente por ID")
    @PutMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, estudianteDTO);
        return ResponseEntity.ok(estudianteActualizado);
    }

    @Operation(summary = "Dar de baja un estudiante por ID")
    @PutMapping("/{id}/baja")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EstudianteDTO> eliminarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO estudianteEliminado = estudianteService.eliminarEstudiante(id, estudianteDTO);
        return ResponseEntity.ok(estudianteEliminado);
    }

    @Operation(summary = "Obtener estudiantes activos")
    @GetMapping("/activos")
    public ResponseEntity<List<EstudianteDTO>> obtenerEstudianteActivo() {
        List<EstudianteDTO> estudiantesActivos = estudianteService.obtenerEstudianteActivo();
        return ResponseEntity.ok(estudiantesActivos);
    }

}
