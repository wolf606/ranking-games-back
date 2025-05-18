package com.hailey.nosql.service;

import com.hailey.nosql.dto.JuegoRequestDTO;
import com.hailey.nosql.dto.JuegoResponseDTO;
import com.hailey.nosql.exception.JuegoExceptions;
import com.hailey.nosql.mapper.JuegoMapper;
import com.hailey.nosql.model.Juego;
import com.hailey.nosql.model.JuegoConsulta;
import com.hailey.nosql.repository.JuegoConsultaRepository;
import com.hailey.nosql.repository.JuegoRepository;
import com.hailey.nosql.repository.JuegoConsultaRepository;
import com.hailey.nosql.util.Pair;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JuegoService {

    private final JuegoRepository juegoRepository;
    private final JuegoConsultaRepository juegoConsultaRepository;

    @Autowired
    public JuegoService(JuegoRepository juegoRepository, JuegoConsultaRepository juegoConsultaRepository) {
        this.juegoRepository = juegoRepository;
        this.juegoConsultaRepository = juegoConsultaRepository;
    }

    public JuegoResponseDTO registrarJuego(JuegoRequestDTO requestDTO) {
        Juego juego = JuegoMapper.toEntity(requestDTO);
        return JuegoMapper.toDTO(juegoRepository.insertarJuego(juego));
    }

    public List<JuegoResponseDTO> obtenerTodosLosJuegos() {
        return juegoRepository.listarJuegos().stream()
                .map(JuegoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public JuegoResponseDTO buscarJuegoPorNombre(String nombre) {
        return juegoRepository.buscarPorNombre(nombre)
                .map(juego -> {
                    JuegoConsulta consulta = new JuegoConsulta();
                    consulta.setGameId(juego.getId());
                    consulta.setGame(juego);
                    consulta.setFechaConsulta(new Date());
                    juegoConsultaRepository.insertarConsulta(consulta);

                    return JuegoMapper.toDTO(juego);
                })
                .orElseThrow(() -> new JuegoExceptions.JuegoNotFoundException(nombre));
    }

    public JuegoResponseDTO eliminarJuegoPorNombre(String nombre) {
        return JuegoMapper.toDTO(juegoRepository.eliminarPorNombre(nombre));
    }

    public List<Pair<String, Long>> obtenerRankingConsultas() {
        return juegoConsultaRepository.obtenerRankingConsultas();
    }
}
