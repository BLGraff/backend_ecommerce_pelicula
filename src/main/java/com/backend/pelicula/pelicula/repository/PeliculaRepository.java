package com.backend.pelicula.pelicula.repository;

import com.backend.pelicula.pelicula.domain.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}
