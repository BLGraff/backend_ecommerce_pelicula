package com.backend.pelicula.pelicula.controller;

import com.backend.pelicula.pelicula.dto.PeliculaDTO;
import com.backend.pelicula.pelicula.service.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pelicula")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @PostMapping
    @ApiResponse(responseCode = "201")
    @Operation(summary = "create  Test", security = @SecurityRequirement(name = "Bearer"))
    @PreAuthorize("hasRole('admin')")
    public PeliculaDTO save(@RequestBody PeliculaDTO peliculaDTO) {
        return peliculaService.save(peliculaDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "create  Test", security = @SecurityRequirement(name = "Bearer"))
    public PeliculaDTO getPeliculaById(@PathVariable Long id) throws Exception {
        return peliculaService.findById(id);
    }

    @GetMapping("/public")
    public List<PeliculaDTO> getPelicula(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String genero
    ) throws Exception {
        return peliculaService.findAll(titulo, genero);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update  Test", security = @SecurityRequirement(name = "Bearer"))
    @PreAuthorize("hasRole('admin')")
    public PeliculaDTO edit(@PathVariable Long id, @RequestBody PeliculaDTO peliculaDTO) throws Exception {
        return peliculaService.update(id, peliculaDTO);
    }
}
