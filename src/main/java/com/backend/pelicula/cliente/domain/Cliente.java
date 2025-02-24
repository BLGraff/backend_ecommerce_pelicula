package com.backend.pelicula.cliente.domain;

import com.backend.pelicula.comentario.domain.Comentario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nombre;

    private String apellido;

    private String telefono;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

}
