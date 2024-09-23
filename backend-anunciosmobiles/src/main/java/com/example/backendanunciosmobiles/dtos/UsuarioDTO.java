package com.example.backendanunciosmobiles.dtos;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String username;
    private String password;
    private boolean isAdmin;

    // Getters y setters
}
