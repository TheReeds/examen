package com.example.backendanunciosmobiles.controllers;

import com.example.backendanunciosmobiles.models.Anuncio;
import com.example.backendanunciosmobiles.models.Participante;
import com.example.backendanunciosmobiles.services.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private AnuncioService anuncioService;

    @GetMapping
    public List<Anuncio> getAnuncios() {
        return anuncioService.getAllAnuncios();
    }

    @PostMapping
    public ResponseEntity<Anuncio> createAnuncio(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam("imagen") MultipartFile imagenFile) {

        String imagen = uploadFile(imagenFile);
        if (imagen == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Anuncio anuncio = new Anuncio();
        anuncio.setNombre(nombre);
        anuncio.setDescripcion(descripcion);
        anuncio.setImagenUrl(imagen);
        anuncio.setFechaInicio(fechaInicio);
        anuncio.setFechaFin(fechaFin);
        anuncio.setParticipantesCount(0);
        Anuncio nuevoAnuncio = anuncioService.createAnuncio(anuncio);

        return new ResponseEntity<>(nuevoAnuncio, HttpStatus.CREATED);
    }


    private String uploadFile(MultipartFile file) {
        try {
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                boolean created = uploadDirectory.mkdirs();
                System.out.println("Directorio creado: " + created);
            }

            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            file.transferTo(filePath.toFile());
            System.out.println("Archivo guardado en: " + filePath.toString());
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    @PostMapping("/{id}/participar")
    public ResponseEntity<Participante> registerParticipant(@PathVariable Long id, @RequestBody Participante participante) {
        Anuncio anuncio = anuncioService.getAnuncioById(id);
        if (anuncio != null) {
            // Comprobar si el participante ya existe
            for (Participante p : anuncio.getParticipantes()) {
                if (p.getEmail().equals(participante.getEmail())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT); // Participante ya registrado
                }
            }

            participante.setAnuncio(anuncio);
            anuncio.getParticipantes().add(participante);
            anuncio.setParticipantesCount(anuncio.getParticipantesCount() + 1);
            anuncioService.updateAnuncio(anuncio);
            return new ResponseEntity<>(participante, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnuncio(@PathVariable Long id) {
        anuncioService.deleteAnuncio(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}