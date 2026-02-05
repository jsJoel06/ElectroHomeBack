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
    public ResponseEntity<?> save(
            @RequestPart("producto") String productoJson ,
            @RequestPart("imagen") MultipartFile imagen
    ){
        try{
            Producto producto = objectMapper.readValue(productoJson, Producto.class);

            Producto productoGuardado = productoRepository.save(producto, imagen);

            return ResponseEntity.ok(productoGuardado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
