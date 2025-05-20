package com.universidad.service;

import com.universidad.dto.InscripcionDTO;
import java.util.List;

public interface IInscripcionService {
    InscripcionDTO crear(InscripcionDTO dto);
    InscripcionDTO actualizar(Long id, InscripcionDTO dto);
    void eliminar(Long id);
    InscripcionDTO obtenerPorId(Long id);
    List<InscripcionDTO> listarTodos();
}
