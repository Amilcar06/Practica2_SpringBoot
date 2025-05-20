package com.universidad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "evaluacion_docente") // Nombre de la tabla en la base de datos
public class EvaluacionDocente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    @JsonBackReference
    private Docente docente;
    private Integer puntuacion;
    private String comentario;
    private java.time.LocalDate fecha;
}