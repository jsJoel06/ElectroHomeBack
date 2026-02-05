package com.empleos.electrohome.service;

import com.empleos.electrohome.models.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {

    List<Producto> findAll();

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByNombre(String nombre);

    List<Producto> findByMarca(String marca);

    Producto findById(Long id);

    Producto save(Producto producto, MultipartFile imagen) throws Exception;

    void delete(Long id);

    Producto update(Long id, Producto producto, MultipartFile imagen) throws Exception;
}
