package com.hailey.nosql.controller;

import com.hailey.nosql.dto.JuegoRequestDTO;
import com.hailey.nosql.dto.JuegoResponseDTO;
import com.hailey.nosql.service.JuegoService;
import com.hailey.nosql.util.Pair;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/juegos")
@CrossOrigin(origins = "*")
public class JuegoController {

    private final JuegoService juegoService;

    @Autowired
    public JuegoController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping
    public JuegoResponseDTO insertarJuego(@RequestBody JuegoRequestDTO juegoDTO) {
        return juegoService.registrarJuego(juegoDTO);
    }

    @GetMapping
    public List<JuegoResponseDTO> listarJuegos() {
        return juegoService.obtenerTodosLosJuegos();
    }

    @GetMapping("/buscar")
    public JuegoResponseDTO buscarPorNombre(@RequestParam String nombre) {
        System.out.println("Buscado juego por nombre: " + nombre);
        return juegoService.buscarJuegoPorNombre(nombre);
    }

    @DeleteMapping("/eliminar")
    public JuegoResponseDTO eliminarPorNombre(@RequestParam String nombre) {
        return juegoService.eliminarJuegoPorNombre(nombre);
    }

    @GetMapping("/ranking")
    public List<Pair<String, Long>> obtenerRankingConsultas() {
        return juegoService.obtenerRankingConsultas();
    }
}
