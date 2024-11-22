package com.backend.pelicula.comentario.repository;

import com.backend.pelicula.comentario.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
