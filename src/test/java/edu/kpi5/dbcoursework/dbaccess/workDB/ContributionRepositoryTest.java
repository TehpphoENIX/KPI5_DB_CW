package edu.kpi5.dbcoursework.dbaccess.workDB;

import edu.kpi5.dbcoursework.configuration.RedisTestConfig;
import edu.kpi5.dbcoursework.entities.workDB.Contribution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest(classes = RedisTestConfig.class)
@Import(RedisTestConfig.class)
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
        Contribution redisEntity = new Contribution(1l);
        redisEntity.get().put(LocalDate.of(2022,01,5),1);
        redisEntity.get().put(LocalDate.of(2022,01,11),2);
        redisEntity.get().put(LocalDate.of(2022,01,16),3);

        underTest.save(redisEntity);
        Contribution recieved = underTest.findById(redisEntity.getId()).get();

        for (var item :
                redisEntity.get().entrySet()) {
            assertEquals(item.getValue(),recieved.get().get(item.getKey()));
        }
    }
}