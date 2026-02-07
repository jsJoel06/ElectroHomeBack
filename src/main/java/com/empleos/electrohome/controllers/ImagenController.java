package com.empleos.electrohome.controllers;

import com.empleos.electrohome.models.Fotos;
import com.empleos.electrohome.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImagenController {


    @Autowired
    private FotoRepository fotoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> verImagen(@PathVariable Long id) {
        Optional<Fotos> fotoOptional = fotoRepository.findById(id);

        if (fotoOptional.isPresent()) {
            byte[] data = fotoOptional.get().getImagenUrl();

            return ResponseEntity.ok()
                    .cacheControl(CacheControl.noCache())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(data);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
