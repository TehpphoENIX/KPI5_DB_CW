package edu.kpi5.dbcoursework.dbaccess.workDB;

import edu.kpi5.dbcoursework.entities.workDB.RedisEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
class ContributionRepositoryTest {

    @Autowired
    ContributionRepository underTest;
    @Autowired
    RedisServerCommands redisServerCommands;

    @AfterEach
    public void clean(){
        redisServerCommands.flushDb();
    }

    @Test
    public void structureTest(){
        //given

        RedisEntity redisEntity = new RedisEntity(1l,new ArrayList<>());
        redisEntity.getContribution().add(Pair.of(LocalDate.now(),1));
        redisEntity.getContribution().add(Pair.of(LocalDate.now(),2));
        redisEntity.getContribution().add(Pair.of(LocalDate.now(),3));
        underTest.save(redisEntity);
        //then
        int i = 1;
        i++;
    }
}