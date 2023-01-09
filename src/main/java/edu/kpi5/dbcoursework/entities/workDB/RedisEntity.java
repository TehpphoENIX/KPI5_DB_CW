package edu.kpi5.dbcoursework.entities.workDB;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.util.Pair;

@RedisHash("RedisEntity")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RedisEntity that = (RedisEntity) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
