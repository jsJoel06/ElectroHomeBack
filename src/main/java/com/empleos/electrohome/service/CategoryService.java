package com.empleos.electrohome.service;

import com.empleos.electrohome.models.Categoria;

import java.util.List;

public interface CategoryService {

    List<Categoria> findAll();

    Categoria findById(Long id);

    Categoria save(Categoria categoria);

    Categoria update(Long id, Categoria categoria);

    void delete(Long id);
}
