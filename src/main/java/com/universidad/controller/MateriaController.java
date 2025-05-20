package com.universidad.controller;

import com.universidad.dto.MateriaDTO;
import com.universidad.service.IMateriaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/materias")
@Tag(name = "Materia", description = "API para gestión de materias")
public class MateriaController {

    private final IMateriaService materiaService;
    private static final Logger logger = LoggerFactory.getLogger(MateriaController.class);

    @Autowired
    public MateriaController(IMateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @Operation(summary = "Obtener todas las materias")
    @ApiResponse(responseCode = "200", description = "Listado de materias obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> obtenerTodasLasMaterias() {
        //long inicio = System.currentTimeMillis();
        //logger.info("[MATERIA] Inicio obtenerTodasLasMaterias: {}", inicio);
        List<MateriaDTO> result = materiaService.obtenerTodasLasMaterias();
        //long fin = System.currentTimeMillis();
        //logger.info("[MATERIA] Fin obtenerTodasLasMaterias: {} (Duracion: {} ms)", fin, (fin-inicio));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener materia por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materia encontrada"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorId(@PathVariable Long id) {
        MateriaDTO materia = materiaService.obtenerMateriaPorId(id);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @Operation(summary = "Obtener materia por código único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materia encontrada"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @GetMapping("/codigo/{codigoUnico}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorCodigoUnico(@PathVariable String codigoUnico) {
        MateriaDTO materia = materiaService.obtenerMateriaPorCodigoUnico(codigoUnico);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @Operation(summary = "Crear una nueva materia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Materia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })

    @PostMapping
    public ResponseEntity<MateriaDTO> crearMateria(@Valid @RequestBody MateriaDTO materia) {
        MateriaDTO nueva = materiaService.crearMateria(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualizar una materia existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materia actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> actualizarMateria(@PathVariable Long id, @Valid @RequestBody MateriaDTO materia) {
        MateriaDTO actualizadaDTO = materiaService.actualizarMateria(id, materia);
        return ResponseEntity.ok(actualizadaDTO);
    }

    @Operation(summary = "Eliminar materia por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Materia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar si agregar un prerequisito formaría un círculo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado de la verificación devuelto"),
            @ApiResponse(responseCode = "404", description = "Materia o prerequisito no encontrado"),
            @ApiResponse(responseCode = "400", description = "Ciclo detectado")
    })
    @GetMapping("/formaria-circulo/{materiaId}/{prerequisitoId}")
    @Transactional
    public ResponseEntity<Boolean> verificarCiclo(
            @PathVariable Long materiaId,
            @PathVariable Long prerequisitoId) {
        boolean hayCiclo = materiaService.formariaCirculo(materiaId, prerequisitoId);
        if (hayCiclo) {
            return ResponseEntity.badRequest().body(true); // ciclo detectado
        }
        return ResponseEntity.ok(false); // no hay ciclo
    }
}
