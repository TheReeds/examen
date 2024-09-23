package com.example.backendanunciosmobiles.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    // Campo para almacenar la URL de la imagen
    private String imagenUrl;
    // Contador de participantes
    private int participantesCount = 0;

    // Relación con participantes (opcional)
    @OneToMany(mappedBy = "anuncio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participante> participantes = new ArrayList<>();

    // Getters y setters
}
