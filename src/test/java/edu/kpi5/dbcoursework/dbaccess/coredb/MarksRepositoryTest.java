package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.dbaccess.marksdb.MarksListRepository;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList.Mark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MarksRepositoryTest {

    @Autowired
    private MarksListRepository underTest;
    @Autowired
    private MarksListRepository marksListRepository;

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

    @Test
    void sameEntitiesCount() {
        assertEquals(underTest.count(), marksList.size());
    }

    @Test
    void findByCourseId() {
        var received = underTest.findById("Mathematica").get();
        assertNotNull(received);
        assertIterableEquals(received.getField(), marksList.get(0).getField());

        var received2 = underTest.findById("History").get();
        assertNotNull(received2);
        assertIterableEquals(received2.getField(), marksList.get(1).getField());

    }


}
