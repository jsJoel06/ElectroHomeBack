package com.empleos.electrohome.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

@Data
@Entity
@Table(name = "fotos")
@AllArgsConstructor
@NoArgsConstructor
public class Fotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Añade esto para indicar que es un objeto grande
    @Basic(fetch = FetchType.LAZY) // <--- ESTO ES CLAVE
    @Column(name = "imagen_url")
    @JdbcType(VarbinaryJdbcType.class)
    @JsonIgnore
    private byte[] imagenUrl;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties("fotos")
    private Producto producto;

    public Fotos(byte[] imagenData, Producto producto) {
        this.imagenUrl = imagenData;
        this.producto = producto;
    }
}
