package com.backend.pelicula.pelicula.dto;

import com.backend.pelicula.pelicula.domain.Condicion;
import com.backend.pelicula.pelicula.domain.Formato;
import com.backend.pelicula.pelicula.domain.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data //getters and setters, toSrings y equals
@AllArgsConstructor
public class PeliculaDTO {

    private Long id;

    private String titulo;

    private LocalDate fechaSalida;

    private Double precio;

    private String imagen;

    private int rating;

    private Formato formato;

    private Condicion condicion;

    private Genero genero;

    private String resumen;

}
