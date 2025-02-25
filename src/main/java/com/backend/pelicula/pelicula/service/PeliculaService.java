package com.backend.pelicula.pelicula.service;

import com.backend.pelicula.pelicula.domain.Pelicula;
import com.backend.pelicula.pelicula.dto.PeliculaDTO;
import com.backend.pelicula.pelicula.repository.PeliculaRepository;
import com.backend.pelicula.pelicula.repository.PeliculaSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private PeliculaPublisher peliculaPublisher;

    public PeliculaDTO save(PeliculaDTO peliculaDTO) {
        Pelicula pelicula = new Pelicula(
                null,
                peliculaDTO.getTitulo(),
                peliculaDTO.getFechaSalida(),
                peliculaDTO.getPrecio(),
                peliculaDTO.getImagen(),
                peliculaDTO.getRating(),
                peliculaDTO.getFormato(),
                peliculaDTO.getCondicion(),
                peliculaDTO.getGenero(),
                peliculaDTO.getResumen(),
                new ArrayList<>()
        );

        Pelicula peliRepo = peliculaRepository.save(pelicula);

        peliculaPublisher.sendPelicula("ADD", peliRepo);

        return peliculaDTO;
    }

    public List<PeliculaDTO> findAll(String titulo, String genero) {

        Specification<Pelicula> spec = Specification
                .where(PeliculaSpecifications.titleContains(titulo)
                        .and(PeliculaSpecifications.genreEquals(genero)));

        return peliculaRepository.findAll(spec).stream().map(
                p -> new PeliculaDTO(
                        p.getId(),
                        p.getTitulo(),
                        p.getFechaSalida(),
                        p.getPrecio(),
                        p.getImagen(),
                        p.getRating(),
                        p.getFormato(),
                        p.getCondicion(),
                        p.getGenero(),
                        p.getResumen()
                )
        ).collect(Collectors.toList());
    }

    public PeliculaDTO findById(Long id) {
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(id);
        if (peliculaOptional.isEmpty()) {
            throw new RuntimeException("Id invalido");
        }
        Pelicula pelicula = peliculaOptional.get();

        return new PeliculaDTO(
                pelicula.getId(),
                pelicula.getTitulo(),
                pelicula.getFechaSalida(),
                pelicula.getPrecio(),
                pelicula.getImagen(),
                pelicula.getRating(),
                pelicula.getFormato(),
                pelicula.getCondicion(),
                pelicula.getGenero(),
                pelicula.getResumen()
        );
    }

    public PeliculaDTO update(Long id, PeliculaDTO peliculaDTO) throws Exception {
        // Buscar la película por ID
        Pelicula p = peliculaRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la película con ID: " + id));

        // Actualizar los campos de la película
        p.setTitulo(peliculaDTO.getTitulo());

        p.setPrecio(peliculaDTO.getPrecio());
        p.setImagen(peliculaDTO.getImagen());
        p.setFormato(peliculaDTO.getFormato());
        p.setCondicion(peliculaDTO.getCondicion());
        p.setGenero(peliculaDTO.getGenero());
        p.setResumen(peliculaDTO.getResumen());

        Pelicula peliRepo = peliculaRepository.save(p);

        peliculaPublisher.sendPelicula("EDIT", peliRepo);

        return peliculaDTO;
    }

    public void delete(Long id) {

        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(id);
        if (peliculaOptional.isEmpty()) {
            throw new RuntimeException("Id invalido");
        }

        peliculaRepository.deleteById(id);

        peliculaPublisher.sendPelicula("DELETE", peliculaOptional.get());
    }

}
