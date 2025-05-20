package com.universidad.service;

import com.universidad.dto.EvaluacionDocenteDTO;
import com.universidad.model.EvaluacionDocente;
import java.util.List;

public interface IEvaluacionDocenteService {
    EvaluacionDocente crearEvaluacion(EvaluacionDocenteDTO evaluacion);
    List<EvaluacionDocente> obtenerEvaluacionesPorDocente(Long docenteId);
    EvaluacionDocente obtenerEvaluacionPorId(Long id);
    void eliminarEvaluacion(Long id);
    EvaluacionDocente actualizarEvaluacion(Long id, EvaluacionDocente evaluacion);
    List<EvaluacionDocente> obtenerTodas();
}
