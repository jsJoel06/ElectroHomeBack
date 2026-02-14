package com.empleos.electrohome.service.impl;

import com.empleos.electrohome.models.DetallePedido;
import com.empleos.electrohome.models.EPedido;
import com.empleos.electrohome.models.Pedido;
import com.empleos.electrohome.models.Producto;
import com.empleos.electrohome.repository.PedidoRepository;
import com.empleos.electrohome.repository.ProductoRepository;
import com.empleos.electrohome.service.PedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Pedido> findByEmailCliente(String emailCliente) {
        return pedidoRepository.findByEmailCliente(emailCliente);
    }

    @Override
    public Pedido obtenerPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }


    @Override
    public Pedido crearPedido(String nombre, String telefono, String email, String direccion) {
        Pedido pedido = new Pedido();
        pedido.setEstado(EPedido.PENDIENTE);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setNombreCliente(nombre);
        pedido.setTelefonoCliente(telefono);
        pedido.setEmailCliente(email);
        pedido.setDireccionEnvio(direccion);
        pedido.setTotal(0.0);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido agregarProducto(Long pedidoId, Long productoId, Integer cantidad) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setPedido(pedido);
        detallePedido.setProducto(producto);
        detallePedido.setCantidad(cantidad);
        detallePedido.setPrecioUnitario(producto.getPrecio());

        pedido.getDetalles().add(detallePedido);

        double total = pedido.getDetalles().stream()
                .mapToDouble( d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();
        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido confirmarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(EPedido.ENTREGADO);
        return pedidoRepository.save(pedido);
    }
}
