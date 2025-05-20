package com.universidad.service.impl;

import com.universidad.model.Materia;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;
import com.universidad.dto.MateriaDTO;
import com.universidad.validation.MateriaValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaValidator materiaValidator;

    MateriaDTO mapToDTO(Materia materia) {
        if (materia == null) return null;
        return MateriaDTO.builder()
                .id(materia.getId())
                .nombreMateria(materia.getNombreMateria())
                .codigoUnico(materia.getCodigoUnico())
                .creditos(materia.getCreditos())
                .prerequisitos(materia.getPrerequisitos() != null ?
                        materia.getPrerequisitos().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                .esPrerequisitoDe(materia.getEsPrerequisitoDe() != null ?
                        materia.getEsPrerequisitoDe().stream().map(Materia::getId).collect(Collectors.toList()) : null)
                .build();
    }

    @Override
    @Cacheable(value = "materias")
    public List<MateriaDTO> obtenerTodasLasMaterias() {
        return materiaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "materia", key = "#id")
    public MateriaDTO obtenerMateriaPorId(Long id) {
        return materiaRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    @Override
    @Cacheable(value = "materia", key = "#codigoUnico")
    public MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico) {
        Optional<Materia> materia = materiaRepository.findByCodigoUnico(codigoUnico);
        return mapToDTO(materia.orElse(null));
    }

    @Transactional
    @Override
    @CachePut(value = "materia", key = "#result.id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
        materiaValidator.validacionCompletaMateria(materiaDTO);

        Materia materia = new Materia();
        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        // Map other fields as necessary

        Materia savedMateria = materiaRepository.save(materia);
        return mapToDTO(savedMateria);
    }

    @Transactional
    @Override
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    public MateriaDTO actualizarMateria(Long id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Materia not found"));

        // Si vas a permitir cambiar el código único, deberías asegurarte que no esté duplicado
        materiaValidator.validaNombreMateria(materiaDTO.getNombreMateria());
        materiaValidator.validaCreditos(materiaDTO.getCreditos());
        if (!materia.getCodigoUnico().equals(materiaDTO.getCodigoUnico())) {
            materiaValidator.validaCodigoUnico(materiaDTO.getCodigoUnico());
        }

        materia.setNombreMateria(materiaDTO.getNombreMateria());
        materia.setCodigoUnico(materiaDTO.getCodigoUnico());
        materia.setCreditos(materiaDTO.getCreditos());
        // Map other fields as necessary

        Materia updatedMateria = materiaRepository.save(materia);
        return mapToDTO(updatedMateria);
    }

    @Transactional
    @Override
    @CacheEvict(value = {"materia", "materias"}, allEntries = true)
    public void eliminarMateria(Long id) {
        materiaRepository.deleteById(id);
    }

    @Override
    public Materia obtenerMateriaPorIdEntidad(Long id) {
        return materiaRepository.findById(id).orElse(null);
    }

    public boolean formariaCirculo(Long materiaId, Long prerequisitoId) {
        return formariaCirculoRecursivo(materiaId, prerequisitoId, new HashSet<>());
    }

    private boolean formariaCirculoRecursivo(Long objetivoId, Long actualId, Set<Long> visitados) {
        if (objetivoId.equals(actualId)) return true;
        if (!visitados.add(actualId)) return false;

        Materia actual = materiaRepository.findById(actualId).orElse(null);
        if (actual == null || actual.getPrerequisitos() == null) return false;

        for (Materia prereq : actual.getPrerequisitos()) {
            if (prereq != null && formariaCirculoRecursivo(objetivoId, prereq.getId(), visitados)) {
                return true;
            }
        }
        return false;
    }
}
