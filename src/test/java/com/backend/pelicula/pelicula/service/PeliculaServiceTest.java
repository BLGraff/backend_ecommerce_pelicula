package com.backend.pelicula.pelicula.service;

import com.backend.pelicula.pelicula.domain.Condicion;
import com.backend.pelicula.pelicula.domain.Formato;
import com.backend.pelicula.pelicula.domain.Genero;
import com.backend.pelicula.pelicula.domain.Pelicula;
import com.backend.pelicula.pelicula.dto.PeliculaDTO;
import com.backend.pelicula.pelicula.repository.PeliculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeliculaServiceTest {

    @Mock
    private PeliculaRepository peliculaRepository;

    @Mock
    private PeliculaPublisher peliculaPublisher;

    @InjectMocks
    private PeliculaService peliculaService;

    private Pelicula pelicula;
    private PeliculaDTO peliculaDTO;

    @BeforeEach
    void setUp() {
        LocalDate fechaSalida = LocalDate.of(1997, 12, 19);  // Definir la fecha correctamente

        pelicula = new Pelicula(1L, "Titanic", fechaSalida, 2000.0, "imagen.jpg", 5, Formato.CD, Condicion.USADO, Genero.DRAMA, "Resumen", List.of());
        peliculaDTO = new PeliculaDTO(1L, "Titanic", fechaSalida, 2000.0, "imagen.jpg", 5, Formato.CD, Condicion.USADO, Genero.DRAMA, "Resumen");
    }

    @Test
    void testGuardarPelicula() {
        PeliculaDTO resultado = peliculaService.save(peliculaDTO);

        assertNotNull(resultado);
        assertEquals(peliculaDTO, resultado);
        assertEquals("Titanic", resultado.getTitulo());
    }

    @Test
    void testBuscarPeliculaPorTituloYGenero() {
        when(peliculaRepository.findAll(any(Specification.class))).thenReturn(List.of(pelicula));

        List<PeliculaDTO> resultado = peliculaService.findAll("Titanic", "Drama");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Titanic", resultado.get(0).getTitulo());
    }

    @Test
    void testBuscarPeliculaPorId() {
        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));

        PeliculaDTO resultado = peliculaService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Titanic", resultado.getTitulo());
    }

    @Test
    void testBuscarPeliculaQueNoExiste() {
        when(peliculaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> peliculaService.findById(1L));

        assertEquals("Id invalido", exception.getMessage());
    }

    @Test
    void testActualizarPelicula() throws Exception {
        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));
        when(peliculaRepository.save(any(Pelicula.class))).thenReturn(pelicula);

        PeliculaDTO resultado = peliculaService.update(1L, peliculaDTO);

        assertNotNull(resultado);
        assertEquals("Titanic", resultado.getTitulo());
        verify(peliculaPublisher, times(1)).sendPelicula(eq("EDIT"), any(Pelicula.class));
    }

    @Test
    void testActualizarPeliculaQueNoExiste() {
        when(peliculaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> peliculaService.update(1L, peliculaDTO));

        assertTrue(exception.getMessage().contains("No se encontró la película con ID"));
    }

    @Test
    void testEliminarPelicula() {
        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));
        doNothing().when(peliculaRepository).deleteById(1L);

        peliculaService.delete(1L);

        verify(peliculaRepository, times(1)).deleteById(1L);
        verify(peliculaPublisher, times(1)).sendPelicula(eq("DELETE"), any(Pelicula.class));
    }

    @Test
    void testEliminarPeliculaQueNoExiste() {
        when(peliculaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> peliculaService.delete(1L));

        assertEquals("Id invalido", exception.getMessage());
    }
}
