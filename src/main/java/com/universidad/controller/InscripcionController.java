package com.universidad.controller;

import com.universidad.dto.InscripcionDTO;
import com.universidad.service.IInscripcionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/inscripciones")
@Tag(name = "Inscripcion", description = "API para gestión de inscripciones")
public class InscripcionController {

    @Autowired
    private IInscripcionService inscripcionService;

    @Operation(summary = "Crear una nueva inscripción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public InscripcionDTO crear(@RequestBody @Valid InscripcionDTO dto) {
        return inscripcionService.crear(dto);
    }

    @Operation(summary = "Actualizar una inscripción existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @PutMapping("/{id}")
    public InscripcionDTO actualizar(@PathVariable Long id, @RequestBody @Valid InscripcionDTO dto) {
        return inscripcionService.actualizar(id, dto);
    }

    @Operation(summary = "Eliminar una inscripción por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        inscripcionService.eliminar(id);
    }

    @Operation(summary = "Obtener inscripción por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción encontrada"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @GetMapping("/{id}")
    public InscripcionDTO obtenerPorId(@PathVariable Long id) {
        return inscripcionService.obtenerPorId(id);
    }

    @Operation(summary = "Listar todas las inscripciones")
    @ApiResponse(responseCode = "200", description = "Listado de inscripciones obtenido correctamente")
    @GetMapping
    public List<InscripcionDTO> listarTodos() {
        return inscripcionService.listarTodos();
    }
}
