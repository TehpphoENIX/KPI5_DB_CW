package edu.kpi5.dbcoursework.dbaccess.marksdb;

import edu.kpi5.dbcoursework.dbaccess.marksdb.MarksListRepository;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList.Mark;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MarksRepositoryTest {

    @Autowired
    private MarksListRepository underTest;
    @Autowired
    private MarksListRepository marksListRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private final ArrayList<MarksList> marksList = new ArrayList<>();
    private final ArrayList<Mark> marks1 = new ArrayList<>();
    private final ArrayList<Mark> marks2 = new ArrayList<>();
    private StudentCourseMarks scm;

    @BeforeEach
    void given() {
        marks1.add(new Mark("lesson1", 5));
        marks1.add(new Mark("lesson2", 4));
        marksList.add(new MarksList("Mathematica", marks1));

        marks1.add(new Mark("lesson44", 1));
        marks1.add(new Mark("lesson45", 2));
        marksList.add(new MarksList("History", marks2));

        marksListRepository.saveAll(marksList);
    }

    @AfterEach
    void clean() {
        mongoTemplate.dropCollection(MarksList.class);
    }

    @Test
    void sameEntitiesCount() {
        assertEquals(marksList.size(), underTest.count());
    }

    @Test
    void findByCourseId() {
        var received = underTest.findById("Mathematica").get();
        assertNotNull(received);
        assertEquals(received, marksList.get(0));

        var received2 = underTest.findById("History").get();
        assertNotNull(received2);
        assertEquals(received2, marksList.get(1));

    }


}
