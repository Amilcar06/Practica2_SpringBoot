package com.universidad.controller;

import com.universidad.dto.EvaluacionDocenteDTO;
import com.universidad.model.EvaluacionDocente;
import com.universidad.service.IEvaluacionDocenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/evaluaciones-docente")
@Tag(name = "Evaluacion Docente", description = "API para gestión de evaluaciones docentes")
public class EvaluacionDocenteController {

    @Autowired
    private IEvaluacionDocenteService evaluacionDocenteService;

    @Operation(summary = "Crear una nueva evaluación docente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evaluación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })

    @PostMapping
    public ResponseEntity<EvaluacionDocente> crearEvaluacion(@RequestBody EvaluacionDocenteDTO evaluacion) {
        EvaluacionDocente nueva = evaluacionDocenteService.crearEvaluacion(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Obtener evaluaciones por ID de docente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluaciones encontradas"),
            @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<EvaluacionDocente>> obtenerEvaluacionesPorDocente(@PathVariable Long docenteId) {
        List<EvaluacionDocente> evaluaciones = evaluacionDocenteService.obtenerEvaluacionesPorDocente(docenteId);
        return ResponseEntity.ok(evaluaciones);
    }

    @Operation(summary = "Obtener evaluación docente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluación encontrada"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionDocente> obtenerEvaluacionPorId(@PathVariable Long id) {
        EvaluacionDocente evaluacion = evaluacionDocenteService.obtenerEvaluacionPorId(id);
        if (evaluacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evaluacion);
    }

    @Operation(summary = "Actualizar una evaluación docente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluación actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionDocente> actualizarEvaluacion(@PathVariable Long id, @RequestBody EvaluacionDocente evaluacionActualizada) {
        EvaluacionDocente actualizada = evaluacionDocenteService.actualizarEvaluacion(id, evaluacionActualizada);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Obtener todas las evaluaciones docentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluaciones encontradas")
    })
    @GetMapping
    public ResponseEntity<List<EvaluacionDocente>> obtenerTodasLasEvaluaciones() {
        List<EvaluacionDocente> evaluaciones = evaluacionDocenteService.obtenerTodas();
        return ResponseEntity.ok(evaluaciones);
    }

    @Operation(summary = "Eliminar evaluación docente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evaluación eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable Long id) {
        evaluacionDocenteService.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
