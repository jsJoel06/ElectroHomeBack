package com.empleos.electrohome.service.impl;

import com.empleos.electrohome.models.Categoria;
import com.empleos.electrohome.repository.CategoriaRepository;
import com.empleos.electrohome.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoryService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return categoria;
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Long id, Categoria categoria) {
        Categoria categoriaActualizada = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoriaActualizada.setNombre(categoria.getNombre());
        return categoriaRepository.save(categoriaActualizada);
    }

    @Override
    public void delete(Long id) {
       categoriaRepository.deleteById(id);
    }
}
