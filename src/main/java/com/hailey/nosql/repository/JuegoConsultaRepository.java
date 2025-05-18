package com.hailey.nosql.repository;

import com.hailey.nosql.util.Pair;
import com.hailey.nosql.model.JuegoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;
import org.bson.Document;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class JuegoConsultaRepository {

    private MongoTemplate mongoTemplate;

    @Autowired
    public JuegoConsultaRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void insertarConsulta(JuegoConsulta consulta) {
        mongoTemplate.insert(consulta);
    }

    public List<JuegoConsulta> listarConsultas() {
        return mongoTemplate.findAll(JuegoConsulta.class);
    }

    public List<Pair<String, Long>> obtenerRankingConsultas() {
        List<JuegoConsulta> consultas = mongoTemplate.findAll(JuegoConsulta.class);

        List<Map<String, Object>> pares = consultas.parallelStream()
                .map(consulta -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("gameId", consulta.getGameId());
                    map.put("count", 1L);
                    return map;
                })
                .collect(Collectors.toList());

        Map<String, Long> conteo = new ConcurrentHashMap<>();
        pares.parallelStream().forEach(par -> {
            String gameId = (String) par.get("gameId");
            conteo.merge(gameId, 1L, Long::sum);
        });

        return conteo.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
