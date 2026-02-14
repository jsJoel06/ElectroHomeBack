package com.empleos.electrohome.controllers;

import com.empleos.electrohome.models.Categoria;
import com.empleos.electrohome.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(
        origins = "https://electrohomes.onrender.com",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true"
)
public class CategoriaController {

    @Autowired
    private CategoryService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll(){
        List<Categoria> categorias = categoriaService.findAll();
        return  categorias != null ? ResponseEntity.ok(categorias)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria){
        return ResponseEntity.ok(categoriaService.save(categoria));
    }

    @GetMapping("/id")
    private ResponseEntity<Categoria> findById(@PathVariable Long id){
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @PutMapping("/update")
    private ResponseEntity<Categoria> update(@PathVariable Long id,@RequestBody Categoria categoria){
        return ResponseEntity.ok(categoriaService.update(id, categoria));
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        categoriaService.delete(id);
    }
}
