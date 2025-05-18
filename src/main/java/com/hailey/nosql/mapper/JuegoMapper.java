package com.hailey.nosql.mapper;

import com.hailey.nosql.dto.JuegoRequestDTO;
import com.hailey.nosql.dto.JuegoResponseDTO;
import com.hailey.nosql.model.Juego;

public class JuegoMapper {

    public static Juego toEntity(JuegoRequestDTO dto) {
        Juego juego = new Juego();
        juego.setNombre(dto.getNombre());
        juego.setGenero(dto.getGenero());
        juego.setPlataforma(dto.getPlataforma());
        juego.setPrecio(dto.getPrecio());
        juego.setStock(dto.getStock());
        juego.setImagen(dto.getImagen());
        return juego;
    }

    public static JuegoResponseDTO toDTO(Juego juego) {
        return new JuegoResponseDTO(
                juego.getId(),
                juego.getNombre(),
                juego.getGenero(),
                juego.getPlataforma(),
                juego.getPrecio(),
                juego.getStock(),
                juego.getImagen()
        );
    }
}
