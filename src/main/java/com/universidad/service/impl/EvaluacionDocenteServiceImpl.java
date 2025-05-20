package com.universidad.service.impl;

import com.universidad.dto.EvaluacionDocenteDTO;
import com.universidad.model.EvaluacionDocente;
import com.universidad.model.Docente;
import com.universidad.repository.EvaluacionDocenteRepository;
import com.universidad.repository.DocenteRepository;
import com.universidad.service.IEvaluacionDocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluacionDocenteServiceImpl implements IEvaluacionDocenteService {
    @Autowired
    private EvaluacionDocenteRepository evaluacionDocenteRepository;
    @Autowired
    private DocenteRepository docenteRepository;

    @Transactional
    @Override
    public EvaluacionDocente crearEvaluacion(EvaluacionDocenteDTO dto) {
        Docente docente = docenteRepository.findById(dto.getDocenteId())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        EvaluacionDocente evaluacion = EvaluacionDocente.builder()
                .comentario(dto.getComentario())
                .fecha(dto.getFecha())
                .puntuacion(dto.getPuntuacion())
                .docente(docente) // Asignación correcta
                .build();

        return evaluacionDocenteRepository.save(evaluacion);
    }
    @Override
    public List<EvaluacionDocente> obtenerEvaluacionesPorDocente(Long docenteId) {
        Docente docente = docenteRepository.findById(docenteId).orElse(null);
        if (docente == null) return java.util.Collections.emptyList();
        return evaluacionDocenteRepository.findByDocente(docente);
    }

    @Override
    public EvaluacionDocente obtenerEvaluacionPorId(Long id) {
        return evaluacionDocenteRepository.findById(id).orElse(null);
    }

    @Override
    public EvaluacionDocente actualizarEvaluacion(Long id, EvaluacionDocente evaluacion) {
        EvaluacionDocente existente = evaluacionDocenteRepository.findById(id).orElse(null);
        if (existente == null) return null;

        // Actualiza campos
        existente.setPuntuacion(evaluacion.getPuntuacion());
        existente.setComentario(evaluacion.getComentario());
        existente.setFecha(evaluacion.getFecha());

        // Si permite cambiar el docente asociado (opcional)
        if (evaluacion.getDocente() != null && evaluacion.getDocente().getId() != null) {
            Docente nuevoDocente = docenteRepository.findById(evaluacion.getDocente().getId()).orElse(null);
            if (nuevoDocente != null) {
                existente.setDocente(nuevoDocente);
            }
        }

        return evaluacionDocenteRepository.save(existente);
    }

    @Override
    public List<EvaluacionDocente> obtenerTodas() {
        return evaluacionDocenteRepository.findAll();
    }

    @Override
    public void eliminarEvaluacion(Long id) {

        evaluacionDocenteRepository.deleteById(id);
    }
}
