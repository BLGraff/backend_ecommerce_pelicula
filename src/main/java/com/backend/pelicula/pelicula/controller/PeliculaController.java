package com.backend.pelicula.pelicula.controller;

import com.backend.pelicula.pelicula.dto.PeliculaDTO;
import com.backend.pelicula.pelicula.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pelicula")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @PostMapping
    public PeliculaDTO save(@RequestBody PeliculaDTO peliculaDTO) {
        return peliculaService.save(peliculaDTO);
    }

    @GetMapping("/{id}")
    public PeliculaDTO getPeliculaById(@PathVariable Long id) throws Exception {
        return peliculaService.findById(id);
    }

    @GetMapping
    public List<PeliculaDTO> getPelicula() throws Exception {
        return peliculaService.findAll();
    }
}
