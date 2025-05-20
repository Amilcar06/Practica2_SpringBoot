package com.universidad.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter // Genera un getter para todos los campos de la clase
@Setter // Genera un setter para todos los campos de la clase
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materia") // Nombre de la tabla en la base de datos
// Esta clase representa una materia en el sistema de gestión de estudiantes
public class Materia implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // Constructor to match the required signature
    public Materia(Long id, String nombreMateria, String codigoUnico) {
        this.id = id;
        this.nombreMateria = nombreMateria;
        this.codigoUnico = codigoUnico;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long id;

    @Column(name = "nombre_materia", nullable = false, length = 100)
    private String nombreMateria;

    @Column(name = "codigo_unico", nullable = false, unique = true)
    private String codigoUnico;

    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @Version
    private Long version;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "materia_prerequisito",
            joinColumns = @JoinColumn(name = "id_materia"),
            inverseJoinColumns = @JoinColumn(name = "id_prerequisito")
    )
    private List<Materia> prerequisitos;

    @ManyToMany(mappedBy = "prerequisitos")
    private List<Materia> esPrerequisitoDe;
}