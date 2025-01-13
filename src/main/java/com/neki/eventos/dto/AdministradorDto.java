package com.neki.eventos.dto;

import com.neki.eventos.model.Administrador;

public record AdministradorDto(
    Long id,
    String nome,
    String email,
    String senha
) {

    public Administrador toEntity() {
        Administrador administrador = new Administrador();
        administrador.setId(this.id);
        administrador.setNome(this.nome);
        administrador.setEmail(this.email);
        administrador.setSenha(this.senha);
        return administrador;
    }

    public static AdministradorDto fromEntity(Administrador administrador) {
        return new AdministradorDto(
            administrador.getId(),
            administrador.getNome(),
            administrador.getEmail(),
            administrador.getSenha()
        );
    }
}
