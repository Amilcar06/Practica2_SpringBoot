package com.universidad.service;

import com.universidad.dto.AsignacionDocenteDTO;

import java.util.List;

public interface IAsignacionDocenteService {
    AsignacionDocenteDTO asignarDocente(AsignacionDocenteDTO dto);
    AsignacionDocenteDTO actualizarAsignacion(Long id, AsignacionDocenteDTO dto);
    List<AsignacionDocenteDTO> obtenerAsignacionesPorDocente(Long docenteId);
    List<AsignacionDocenteDTO> obtenerAsignacionesPorMateria(Long materiaId);
    void eliminarAsignacion(Long id);
    List<AsignacionDocenteDTO> obtenerTodas();
}
