package com.empleos.electrohome.service;

import com.empleos.electrohome.models.Pedido;

public interface PedidoService {

    Pedido obtenerPedido(Long pedidoId);

    Pedido crearPedido(String nombre, String telefono, String email, String direccion);

    Pedido agregarProducto(Long pedidoId, Long productoId, Integer cantidad);

    Pedido confirmarPedido(Long pedidoId);
}
