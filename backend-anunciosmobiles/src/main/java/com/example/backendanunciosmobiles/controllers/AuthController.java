package com.example.backendanunciosmobiles.controllers;

import com.example.backendanunciosmobiles.dtos.JwtAuthenticationResponse;
import com.example.backendanunciosmobiles.dtos.LoginRequest;
import com.example.backendanunciosmobiles.dtos.RegisterUserDto;
import com.example.backendanunciosmobiles.repositories.UsuarioRepository;
import com.example.backendanunciosmobiles.security.JwtTokenProvider;
import com.example.backendanunciosmobiles.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService; // Tu servicio de usuarios

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        usuarioService.createUser(registerUserDto, "USUARIO");
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterUserDto registerUserDto) {
        usuarioService.createUser(registerUserDto, "ADMIN");
        return ResponseEntity.ok("Administrador registrado correctamente");
    }


}
