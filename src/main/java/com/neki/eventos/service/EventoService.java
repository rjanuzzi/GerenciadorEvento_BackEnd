package com.neki.eventos.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.neki.eventos.dto.EventoCadastroDto;
import com.neki.eventos.dto.EventoDto;
import com.neki.eventos.dto.EventoEditarDto;
import com.neki.eventos.model.Administrador;
import com.neki.eventos.model.Evento;
import com.neki.eventos.repository.AdministradorRepository;
import com.neki.eventos.repository.EventoRepository;

import jakarta.transaction.Transactional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repositorio;

    @Autowired
    private AdministradorRepository adminRepositorio;

    public List<EventoDto> listarEventos(String emailAdmin) {
        Administrador admin = adminRepositorio.findByEmail(emailAdmin)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));
        return repositorio.findByAdmin(admin).stream()
                .map(EventoDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public EventoDto salvarEvento(EventoCadastroDto eventoDto) {
        String emailAdmin = SecurityContextHolder.getContext().getAuthentication().getName();

        Administrador admin = adminRepositorio.findByEmail(emailAdmin)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Evento novoEvento = new Evento();
        novoEvento.setNome(eventoDto.nome());
        novoEvento.setDataEvento(eventoDto.dataEvento());
        novoEvento.setLocalizacao(eventoDto.localizacao());
        novoEvento.setAdmin(admin);
        try {
            String caminhoImagem = salvarImagem(eventoDto.imagem());
            novoEvento.setImagem(caminhoImagem);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem", e);
        }

        Evento eventoSalvo = repositorio.save(novoEvento);
        return EventoDto.fromEntity(eventoSalvo);
    }

    public String salvarImagem(MultipartFile imagem) throws IOException {
        String diretorio = "src/main/resources/images/";
        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
        Path caminho = Paths.get(diretorio + nomeArquivo);

        Files.createDirectories(caminho.getParent());

        Files.write(caminho, imagem.getBytes());

        return nomeArquivo;
    }

    @Transactional
    public Optional<EventoDto> alterarEvento(Long id, EventoEditarDto eventoDto) {
        if (!repositorio.existsById(id)) {
            return Optional.empty();
        }

        Evento evento = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        evento.setDataEvento(eventoDto.dataEvento());
        evento.setLocalizacao(eventoDto.localizacao());

        Evento eventoAlterado = repositorio.save(evento);
        return Optional.of(EventoDto.fromEntity(eventoAlterado));
    }

    @Transactional
    public boolean deletarEvento(Long eventoId, String emailAdmin) {
        Administrador admin = adminRepositorio.findByEmail(emailAdmin)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

        Evento evento = repositorio.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (!evento.getAdmin().equals(admin)) {
            throw new RuntimeException("Acesso negado");
        }

        repositorio.deleteById(eventoId);
        return true;
    }
}


