package com.empleos.electrohome.service;

import com.empleos.electrohome.models.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> findByEmailCliente(String emailCliente);

    Pedido obtenerPedido(Long pedidoId);

    Pedido crearPedido(String nombre, String telefono, String email, String direccion);

    Pedido agregarProducto(Long pedidoId, Long productoId, Integer cantidad);

    Pedido confirmarPedido(Long pedidoId);
}
