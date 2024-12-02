package com.backend.pelicula.pelicula.repository;

import com.backend.pelicula.pelicula.domain.Pelicula;
import org.springframework.data.jpa.domain.Specification;

public class PeliculaSpecifications {
    public static Specification<Pelicula> titleContains(String titulo) {
        return (root, query, criteriaBuilder) ->
                titulo == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")),
                        "%" + titulo.toLowerCase() + "%");
    }

    public static Specification<Pelicula> genreEquals(String genero) {
        return (root, query, criteriaBuilder) ->
                genero == null ? null : criteriaBuilder.equal(root.get("genero"), genero);
    }
}
