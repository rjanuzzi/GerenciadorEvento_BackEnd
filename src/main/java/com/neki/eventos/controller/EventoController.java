package com.neki.eventos.controller;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neki.eventos.dto.EventoCadastroDto;
import com.neki.eventos.dto.EventoDto;
import com.neki.eventos.dto.EventoEditarDto;
import com.neki.eventos.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService service;

    @GetMapping
    @Operation(summary = "Retorna todos os eventos.", description = "Exibir todos os eventos com as informações gerais.")
    @ApiResponse(responseCode = "200", description = "Eventos localizados.")
    public List<EventoDto> listarTodos() {
        String emailAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.listarEventos(emailAdmin);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo evento.", description = "Cria um novo evento e retorna os detalhes do evento.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Erro ao criar um novo evento."),
        @ApiResponse(responseCode = "201", description = "Evento criado.")
    })
    public ResponseEntity<EventoDto> cadastrarEvento(@ModelAttribute @Valid EventoCadastroDto eventoDto) {
        try {
            EventoDto eventoCriado = service.salvarEvento(eventoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar um evento.", description = "Altera um evento e retorna os detalhes do evento alterado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "404", description = "Não foi encontrado o evento pelo id informado. Verifique!"),
        @ApiResponse(responseCode = "200", description = "Evento alterado.")
    })
    public ResponseEntity<EventoDto> alterarEvento(@PathVariable Long id, @Valid @RequestBody EventoEditarDto eventoDto) {
        Optional<EventoDto> eventoAlterado = service.alterarEvento(id, eventoDto);

        if (!eventoAlterado.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(eventoAlterado.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um evento.", description = "Apaga um evento de acordo com o id fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "404", description = "Não foi encontrado o evento pelo id informado. Verifique e tente novamente!"),
        @ApiResponse(responseCode = "200", description = "Evento deletado.")
    })
    public ResponseEntity<Void> deletarEvento(@PathVariable Long id) {
        String emailAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!service.deletarEvento(id, emailAdmin)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}

