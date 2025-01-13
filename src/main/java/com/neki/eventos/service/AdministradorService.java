package com.neki.eventos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neki.eventos.config.JwtUtil;
import com.neki.eventos.dto.AdministradorCadastroDto;
import com.neki.eventos.dto.AdministradorDto;
import com.neki.eventos.model.Administrador;
import com.neki.eventos.repository.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository repositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AdministradorDto salvarAdmin(AdministradorCadastroDto adminDto) {
        if (!adminDto.senha().equals(adminDto.confirmaSenha())) {
            throw new IllegalArgumentException("Senhas não conferem");
        }

        validarCadastro(adminDto);

        Administrador admin = new Administrador();
        admin.setNome(adminDto.nome());
        admin.setEmail(adminDto.email());
        admin.setSenha(passwordEncoder.encode(adminDto.senha()));

        Administrador adminSalvo = repositorio.save(admin);

        return AdministradorDto.fromEntity(adminSalvo);
    }

    private void validarCadastro(AdministradorCadastroDto adminDto) {
        if (repositorio.existsByEmail(adminDto.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
    }

    public String autenticar(String email, String senha) {
        Administrador admin = repositorio.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, admin.getSenha())) {
            throw new IllegalArgumentException("Senha inválida");
        }

        return jwtUtil.generateToken(email);
    }
}

