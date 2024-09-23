package com.example.backendanunciosmobiles.controllers;

import com.example.backendanunciosmobiles.dtos.UsuarioDTO;
import com.example.backendanunciosmobiles.models.Anuncio;
import com.example.backendanunciosmobiles.models.Rol;
import com.example.backendanunciosmobiles.models.Usuario;
import com.example.backendanunciosmobiles.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioService.getAllUsers();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Rol role = usuarioDTO.isAdmin() ? Rol.ADMIN : Rol.USUARIO;
        Usuario usuario = usuarioService.createUser(usuarioDTO.getUsername(), usuarioDTO.getPassword(), role);
        return ResponseEntity.ok(usuario);
    }
}
