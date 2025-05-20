package com.universidad.repository;

import com.universidad.model.AsignacionDocente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignacionDocenteRepository extends JpaRepository<AsignacionDocente, Long> {
    List<AsignacionDocente> findByDocenteId(Long docenteId);
    List<AsignacionDocente> findByMateriaId(Long materiaId);
}
