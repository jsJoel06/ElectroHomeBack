package com.empleos.electrohome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "fotos")
@AllArgsConstructor
@NoArgsConstructor
public class Fotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties("fotos")
    private Producto producto;

    public Fotos(String imagenUrl, Producto producto) {
        this.imagenUrl = imagenUrl;
        this.producto = producto;
    }
}
