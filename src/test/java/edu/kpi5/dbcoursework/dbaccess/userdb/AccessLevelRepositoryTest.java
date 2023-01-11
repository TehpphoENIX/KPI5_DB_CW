package edu.kpi5.dbcoursework.dbaccess.userdb;

import edu.kpi5.dbcoursework.entities.userdb.AccessLevel;
import edu.kpi5.dbcoursework.entities.userdb.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccessLevelRepositoryTest {

    @Autowired
    AccessLevelRepository underTest;
    @Autowired
    Neo4jTemplate neo4jTemplate;

    @AfterEach
    public void clean(){
        neo4jTemplate.deleteAll(User.class);
        neo4jTemplate.deleteAll(AccessLevel.class);
    }

    @Test
    public void check(){
        AccessLevel accessLevel = new AccessLevel("test");
        underTest.save(accessLevel);
        AccessLevel recieved = accessLevel = underTest.findById("test").get();
        assertEquals(accessLevel, recieved);
    }

}