package com.empleos.electrohome.controllers;

import com.empleos.electrohome.service.PedidoService;
import org.springframework.web.bind.annotation.*;
import com.empleos.electrohome.models.Pedido;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(
        origins = "https://electrohomes.onrender.com",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true"
)
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/cliente/{email:.+}") // <-- Agrega el :.+
    public List<Pedido> listarPedidosPorCliente(@PathVariable String email) {
        return pedidoService.findByEmailCliente(email);
    }

    @GetMapping("/{pedidoId}")
    public Pedido verPedido(@PathVariable Long pedidoId) {
        return pedidoService.obtenerPedido(pedidoId);
    }


    // 1️⃣ Crear pedido
    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoService.crearPedido(
                pedido.getNombreCliente(),
                pedido.getTelefonoCliente(),
                pedido.getEmailCliente(),
                pedido.getDireccionEnvio()
        );
    }

    // 2️⃣ Agregar producto
    @PostMapping("/{pedidoId}/productos")
    public Pedido agregarProducto(
            @PathVariable Long pedidoId,
            @RequestParam Long productoId,
            @RequestParam Integer cantidad
    ) {
        return pedidoService.agregarProducto(pedidoId, productoId, cantidad);
    }

    // 3️⃣ Confirmar pedido
    @PutMapping("/{pedidoId}/confirmar")
    public Pedido confirmarPedido(@PathVariable Long pedidoId) {
        return pedidoService.confirmarPedido(pedidoId);
    }
}
