package edu.kpi5.dbcoursework.entities.workDB;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.util.Pair;

@RedisHash("RedisEntity")
public class Contribution implements Serializable {

    @Id
    @Getter
    @Setter
    private Long id;

    private Map<LocalDate,Integer> contribution = new HashMap<>();

    public Contribution(Long id) {
        this.id = id;
    }

    public Contribution() {
    }

    public Map<LocalDate,Integer> get(){
        return contribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contribution that = (Contribution) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
