package com.example.backendanunciosmobiles.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    @JsonIgnore // Esto previene la serialización cíclica
    private Anuncio anuncio;


    // Getters y setters
}
