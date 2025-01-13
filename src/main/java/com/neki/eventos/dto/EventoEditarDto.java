package com.neki.eventos.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoEditarDto(
    @NotNull(message = "A data do evento é obrigatória.")
    @Future(message = "A data do evento deve ser futura.")
    LocalDate dataEvento,

    @NotBlank(message = "A localização do evento é obrigatória.")
    String localizacao
) {
}

