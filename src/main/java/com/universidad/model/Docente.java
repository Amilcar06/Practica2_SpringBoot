package com.universidad.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "docente") // Nombre de la tabla en la base de datos  
public class Docente extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "nro_empleado", nullable = false, unique = true) // Columna no nula y con valor único    
    private String nroEmpleado;

    @Column(name = "departamento", nullable = false) // Columna no nula
    private String departamento;

    /**
     * Lista de evaluaciones asociadas al docente.
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EvaluacionDocente> evaluaciones;
}
