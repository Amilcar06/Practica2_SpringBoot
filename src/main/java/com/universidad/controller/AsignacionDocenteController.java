package com.universidad.controller;

import com.universidad.dto.AsignacionDocenteDTO;
import com.universidad.service.IAsignacionDocenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/asignaciones")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Asignación Docente", description = "API para gestión de asignaciones docentes")
public class AsignacionDocenteController {

    private final IAsignacionDocenteService asignacionService;

    @Operation(summary = "Crear una nueva asignación")
    @PostMapping
    public ResponseEntity<AsignacionDocenteDTO> crearAsignacion(@RequestBody @Valid AsignacionDocenteDTO dto) {
        return ResponseEntity.ok(asignacionService.asignarDocente(dto));
    }

    @Operation(summary = "Listar todas las asignaciones")
    @GetMapping
    public ResponseEntity<List<AsignacionDocenteDTO>> listarTodas() {
        // Necesitarás un nuevo método en el service para obtener todas
        return ResponseEntity.ok(asignacionService.obtenerTodas());
    }

    @Operation(summary = "Listar asignaciones por ID de docente")
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<AsignacionDocenteDTO>> listarPorDocente(@PathVariable Long docenteId) {
        return ResponseEntity.ok(asignacionService.obtenerAsignacionesPorDocente(docenteId));
    }

    @Operation(summary = "Listar asignaciones por ID de materia")
    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<List<AsignacionDocenteDTO>> listarPorMateria(@PathVariable Long materiaId) {
        return ResponseEntity.ok(asignacionService.obtenerAsignacionesPorMateria(materiaId));
    }

    @Operation(summary = "Actualizar una asignación por ID")
    @PutMapping("/{id}")
    public ResponseEntity<AsignacionDocenteDTO> actualizarAsignacion(
            @PathVariable Long id,
            @RequestBody @Valid AsignacionDocenteDTO dto) {
        return ResponseEntity.ok(asignacionService.actualizarAsignacion(id, dto));
    }

    @Operation(summary = "Eliminar una asignación por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignacion(@PathVariable Long id) {
        asignacionService.eliminarAsignacion(id);
        return ResponseEntity.noContent().build();
    }
}
