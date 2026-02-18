package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoResponseDTO(
    String id,
    String nombre,
    String descripcion,
    BigDecimal precio,
    Integer stock,
    Boolean activo,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion
) {}
