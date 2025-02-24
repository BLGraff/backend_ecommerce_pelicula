package com.backend.pelicula.comentario.domain;

import com.backend.pelicula.cliente.domain.Cliente;
import com.backend.pelicula.pelicula.domain.Pelicula;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "comentario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private String descripcion;

    private int calificacion;

    @ManyToOne
    @JoinColumn(name = "pelicula_id")
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
