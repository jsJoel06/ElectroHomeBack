package com.empleos.electrohome.service.impl;

import com.empleos.electrohome.models.Fotos;
import com.empleos.electrohome.models.Producto;
import com.empleos.electrohome.repository.ProductoRepository;
import com.empleos.electrohome.service.ProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    private final Path root = Paths.get("uploads");

    @Override
    @Transactional
    public Producto save(Producto producto, MultipartFile imagen) throws Exception {

        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }


        String nombreImagen = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
        Path rutaDestino = root.resolve(nombreImagen);

        try {

            Files.copy(imagen.getInputStream(), rutaDestino);


            Fotos nuevaFoto = new Fotos(nombreImagen, producto);
            if (producto.getFotos() == null) {
                producto.setFotos(new ArrayList<>());
            }
            producto.getFotos().add(nuevaFoto);


            return productoRepository.save(producto);

        } catch (Exception e) {

            Files.deleteIfExists(rutaDestino);
            throw new Exception("Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> findByCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    @Override
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public List<Producto> findByMarca(String marca) {
        return productoRepository.findByMarca(marca);
    }

    @Override
    public Producto findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return producto;
    }


    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);

    }

    @Override
    @Transactional
    public Producto update(Long id, Producto producto, MultipartFile imagen) throws Exception {

        Producto productoActualizado = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoActualizado.setNombre(producto.getNombre());
        productoActualizado.setMarca(producto.getMarca());
        productoActualizado.setPrecio(producto.getPrecio());
        productoActualizado.setStock(producto.getStock());
        productoActualizado.setDescripcion(producto.getDescripcion());
        productoActualizado.setCategoria(producto.getCategoria());

        if (imagen != null && !imagen.isEmpty()) {

            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            String nombreImagen = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
            Path rutaDestino = root.resolve(nombreImagen);

            Files.copy(imagen.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);

            Fotos nuevaFoto = new Fotos(nombreImagen, productoActualizado);

            if (productoActualizado.getFotos() == null) {
                productoActualizado.setFotos(new ArrayList<>());
            }

            productoActualizado.getFotos().add(nuevaFoto);
        }

        return productoRepository.save(productoActualizado);
    }

}
