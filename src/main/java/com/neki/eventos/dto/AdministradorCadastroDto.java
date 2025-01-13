package com.neki.eventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdministradorCadastroDto(
	    @NotBlank(message = "O nome é obrigatório.")
	    String nome,

	    @Email(message = "O email deve ser válido.")
	    @NotBlank(message = "O email é obrigatório.")
	    String email,

	    @NotBlank(message = "A senha é obrigatória.")
	    String senha,

	    @NotBlank(message = "A confirmação da senha é obrigatória.")
	    String confirmaSenha
	) {
	}


