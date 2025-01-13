package com.neki.eventos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.neki.eventos.model.Administrador;
import com.neki.eventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

	List<Evento> findByAdmin(Administrador admin);
}

