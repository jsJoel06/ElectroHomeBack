package com.empleos.electrohome.controllers;

import com.empleos.electrohome.models.Producto;
import com.empleos.electrohome.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(
        origins = "https://tu-url-de-render.onrender.com",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true"
)
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

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> findByCategoria(@PathVariable String categoria){
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return  productos != null ? ResponseEntity.ok(productos)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> findByNombre(@PathVariable String nombre){
        List<Producto> productos = productoRepository.findByNombre(nombre);
        return  productos != null ? ResponseEntity.ok(productos)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> findByMarca(@PathVariable String marca){
        List<Producto> productos = productoRepository.findByMarca(marca);
        return  productos != null ? ResponseEntity.ok(productos)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Producto> findById(@PathVariable Long id){
        Producto producto = productoRepository.findById(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(
            @RequestParam("producto") String productoJson,
            @RequestParam("imagen") MultipartFile imagen
    ){
        try {
            // 1. Convertimos el String JSON a objeto Java
            Producto producto = objectMapper.readValue(productoJson, Producto.class);

            // 2. ¡OJO! Debes llamar al SERVICE, no al Repository.
            // El repositorio de Spring no sabe qué hacer con un MultipartFile.
            Producto productoGuardado = productoRepository.save(producto, imagen);

            return ResponseEntity.ok(productoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestPart("producto") String productoJson, // Consistencia con el save
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        try {
            Producto producto = objectMapper.readValue(productoJson, Producto.class);
            // Llamamos al método update del SERVICE que corregimos antes
            Producto productoActualizado = productoRepository.update(id, producto, imagen);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        productoRepository.delete(id);
    }


}
