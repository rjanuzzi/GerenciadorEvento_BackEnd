package com.neki.eventos.dto;


import java.time.LocalDate;

import com.neki.eventos.model.Administrador;
import com.neki.eventos.model.Evento;

public record EventoDto(
    Long id,
    String nome,
    LocalDate dataEvento,
    String localizacao,
    String imagem,
    Long idAdmin
) {

    public Evento toEntity() {
        Evento evento = new Evento();
        evento.setId(this.id);
        evento.setNome(this.nome);
        evento.setDataEvento(this.dataEvento);
        evento.setLocalizacao(this.localizacao);
        evento.setImagem(this.imagem);
        Administrador admin = new Administrador();
        admin.setId(this.idAdmin);
        evento.setAdmin(admin);
        return evento;
    }

    public static EventoDto fromEntity(Evento evento) {
        return new EventoDto(
            evento.getId(),
            evento.getNome(),
            evento.getDataEvento(),
            evento.getLocalizacao(),
            evento.getImagem(),
            evento.getAdmin().getId()
        );
    }
}

