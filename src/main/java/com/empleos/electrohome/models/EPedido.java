package com.empleos.electrohome.models;

public enum EPedido {
    PENDIENTE,  // El pedido ha sido creado y pagado.
    PROCESANDO, // El personal de almacén está empacando los productos.
    ENVIADO,    // El paquete está en manos del servicio de mensajería.
    ENTREGADO,  // El cliente confirmó la recepción.
    CANCELADO   // El pedido fue anulado por el cliente o por falta de stock.
}