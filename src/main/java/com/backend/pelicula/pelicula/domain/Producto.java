package com.backend.pelicula.pelicula.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Producto {

    private Long id;

    private String nombre;

    private Double precio;
}