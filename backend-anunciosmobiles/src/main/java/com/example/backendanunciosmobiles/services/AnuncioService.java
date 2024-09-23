package com.example.backendanunciosmobiles.services;

import com.example.backendanunciosmobiles.models.Anuncio;
import com.example.backendanunciosmobiles.repositories.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    public List<Anuncio> getAllAnuncios() {
        return anuncioRepository.findAll();
    }

    public Anuncio createAnuncio(Anuncio anuncio) {
        return anuncioRepository.save(anuncio);
    }
    public Anuncio updateAnuncio(Anuncio anuncio) {
        return anuncioRepository.save(anuncio);
    }
    public Anuncio getAnuncioById(Long id) {
        return anuncioRepository.findById(id).orElse(null);
    }
    public void deleteAnuncio(Long id) {
        Anuncio anuncio = getAnuncioById(id);
        if (anuncio != null) {
            // Eliminar la imagen del sistema de archivos
            String imagenPath = "upload-dir-path/" + anuncio.getImagenUrl(); // Asegúrate de ajustar la ruta
            File imagenFile = new File(imagenPath);
            if (imagenFile.exists()) {
                imagenFile.delete();
            }
            // Eliminar el anuncio (que también elimina a los participantes por CascadeType.ALL)
            anuncioRepository.delete(anuncio);
        }
    }
}