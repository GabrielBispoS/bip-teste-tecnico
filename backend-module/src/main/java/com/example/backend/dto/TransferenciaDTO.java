package com.example.backend.dto;

import java.math.BigDecimal;

public record TransferenciaDTO(
        Long fromId,
        Long toId,
        BigDecimal valor
) {}