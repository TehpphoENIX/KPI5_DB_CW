package edu.kpi5.dbcoursework.entities.workDB;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.util.Pair;

@RedisHash("RedisEntity")
public class RedisEntity implements Serializable {

    @Id
    private Long id;
    private ArrayList<Pair<LocalDate,Integer>> contribution;

    public RedisEntity(Long id, ArrayList<Pair<LocalDate,Integer>> contribution) {
        this.id = id;
        this.contribution = contribution;
    }

    public RedisEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<Pair<LocalDate,Integer>> getContribution() {
        return contribution;
    }

    public void setContribution(ArrayList<Pair<LocalDate,Integer>> contribution) {
        this.contribution = contribution;
    }
}
