package com.backend.pelicula.pelicula.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pelicula")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    private Double precio;

    private String imagen;

    private int rating;

    private Formato formato;

    private Condicion condicion;

    private Genero genero;

    private String resumen;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<Participacion> participaciones = new ArrayList<>();

}
