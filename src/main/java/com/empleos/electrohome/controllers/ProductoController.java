package com.empleos.electrohome.controllers;

import com.empleos.electrohome.models.Producto;
import com.empleos.electrohome.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Producto>> findAll(){
        List<Producto> productos = productoRepository.findAll();
        return  productos != null ? ResponseEntity.ok(productos)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Producto>> findByCategoria(String categoria){
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return  productos != null ? ResponseEntity.ok(productos)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<Producto>> findByNombre(String nombre){
        List<Producto> productos = productoRepository.findByNombre(nombre);
        return  productos != null ? ResponseEntity.ok(productos)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/marca")
    public ResponseEntity<List<Producto>> findByMarca(String marca){
        List<Producto> productos = productoRepository.findByMarca(marca);
        return  productos != null ? ResponseEntity.ok(productos)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/id")
    private ResponseEntity<Producto> findById(Long id){
        Producto producto = productoRepository.findById(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(
            @RequestParam("producto") String productoJson, // CAMBIADO A @RequestParam
            @RequestParam("imagen") MultipartFile imagen   // CAMBIADO A @RequestParam
    ){
        try {
            Producto producto = objectMapper.readValue(productoJson, Producto.class);
            // Recuerda que el repositorio estándar no acepta (producto, imagen)
            // Esto debe ir a un servicio que maneje el archivo.
            Producto productoGuardado = productoRepository.save(producto, imagen);
            return ResponseEntity.ok(productoGuardado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> update(
            @PathVariable Long id,
            @RequestPart("producto") Producto producto ,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws Exception {
        producto.setId(id);
        return ResponseEntity.ok(productoRepository.update(id, producto, imagen));
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        productoRepository.delete(id);
    }


}
