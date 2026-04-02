package io.github.alblm28.reserva_cabanas.model;

public enum EstadoPago {
    pendiente,    // falta confirmacion plataforma externa
    completado,
    fallido,      // pago rechazado
    cancelado,
    reembolsado
}
