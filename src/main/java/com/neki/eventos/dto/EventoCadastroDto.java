package com.neki.eventos.dto;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

public record EventoCadastroDto(
		String nome,
		LocalDate dataEvento,
		String localizacao,
		MultipartFile imagem
		) {

}
