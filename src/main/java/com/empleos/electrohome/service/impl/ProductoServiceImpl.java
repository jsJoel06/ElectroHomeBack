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
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    @Transactional
    public Producto save(Producto producto, MultipartFile imagen) throws Exception {
        try {

            byte[] bytesImagen = imagen.getBytes();


            Fotos nuevaFoto = new Fotos(bytesImagen, producto);

            if (producto.getFotos() == null) {
                producto.setFotos(new ArrayList<>());
            }


            producto.getFotos().add(nuevaFoto);


            return productoRepository.save(producto);

        } catch (IOException e) {
            throw new Exception("Error al procesar los bytes de la imagen: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error al guardar el producto en la base de datos: " + e.getMessage());
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

        // 1. Buscamos el producto existente
        Producto productoActualizado = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // 2. Actualizamos los campos básicos
        productoActualizado.setNombre(producto.getNombre());
        productoActualizado.setMarca(producto.getMarca());
        productoActualizado.setPrecio(producto.getPrecio());
        productoActualizado.setStock(producto.getStock());
        productoActualizado.setDescripcion(producto.getDescripcion());
        productoActualizado.setCategoria(producto.getCategoria());

        // 3. Manejo de la imagen en base de datos
        if (imagen != null && !imagen.isEmpty()) {
            try {
                // Extraemos los bytes reales
                byte[] bytesImagen = imagen.getBytes();

                // Opción: Limpiar fotos anteriores antes de agregar la nueva (si solo quieres una foto)
                if (productoActualizado.getFotos() != null) {
                    productoActualizado.getFotos().clear();
                } else {
                    productoActualizado.setFotos(new ArrayList<>());
                }

                // Creamos la nueva entidad Fotos con los bytes
                Fotos nuevaFoto = new Fotos(bytesImagen, productoActualizado);
                productoActualizado.getFotos().add(nuevaFoto);

            } catch (IOException e) {
                throw new Exception("Error al procesar la imagen: " + e.getMessage());
            }
        }

        // 4. Guardamos en Neon
        return productoRepository.save(productoActualizado);
    }

}
