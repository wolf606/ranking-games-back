package com.hailey.nosql.repository;

import com.hailey.nosql.exception.JuegoExceptions;
import com.hailey.nosql.model.Juego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JuegoRepository {

    private MongoTemplate mongoTemplate;

    @Autowired
    public JuegoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Juego insertarJuego(Juego juego) {
        Juego res = mongoTemplate.insert(juego);
        return res;
    }

    public List<Juego> listarJuegos() {
        return mongoTemplate.findAll(Juego.class);
    }

    public Optional<Juego> buscarPorNombre(String nombre) {
        Query query = new Query(Criteria.where("nombre").is(nombre));
        Juego juego = mongoTemplate.findOne(query, Juego.class);
        return Optional.ofNullable(juego);
    }

    public Juego eliminarPorNombre(String nombre) {
        Query query = new Query(Criteria.where("nombre").is(nombre));
        Juego juego = mongoTemplate.findOne(query, Juego.class);
        if (juego != null) {
            mongoTemplate.remove(juego);
            return juego;
        } else {
            throw new JuegoExceptions.JuegoNotFoundException(nombre);
        }
    }

    public boolean existePorNombre(String nombre) {
        Query query = new Query(Criteria.where("nombre").is(nombre));
        return mongoTemplate.exists(query, Juego.class);
    }

    public List<Juego> buscarPorIds(List<String> ids) {
        Query query = new Query(Criteria.where("id").in(ids));
        return mongoTemplate.find(query, Juego.class);
    }
}
