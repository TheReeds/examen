package com.example.backendanunciosmobiles.services;

import com.example.backendanunciosmobiles.dtos.RegisterUserDto;
import com.example.backendanunciosmobiles.models.Rol;
import com.example.backendanunciosmobiles.models.Usuario;
import com.example.backendanunciosmobiles.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsers(){
        return usuarioRepository.findAll();
    }
    public Usuario createUser(String username, String password, Rol role) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(role);
        return usuarioRepository.save(usuario);
    }

    public Usuario createUser(RegisterUserDto userDto, String roleName) {
        Rol role = Rol.valueOf(roleName.toUpperCase());
        return createUser(userDto.getUsername(), userDto.getPassword(), role);
    }


    public Optional<Usuario> getUserByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
