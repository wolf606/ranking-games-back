package com.hailey.nosql.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "consultas_juegos")
public class JuegoConsulta {

    @Id
    private String id;
    private String gameId;
    private Juego game;
    private Date fechaConsulta;

    public JuegoConsulta() {}

    public JuegoConsulta(String gameId, Juego game, Date fechaConsulta) {
        this.gameId = gameId;
        this.game = game;
        this.fechaConsulta = fechaConsulta;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Juego getGame() {
        return game;
    }
    public void setGame(Juego game) {
        this.game = game;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }
    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

}
