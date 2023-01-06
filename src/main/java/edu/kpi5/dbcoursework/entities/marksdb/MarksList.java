package edu.kpi5.dbcoursework.entities.marksdb;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document("student_course_marks")
public class MarksList {
    @Id
    private String id;

    public static class Mark {
        public String key;
        public int value;

        public Mark(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    private ArrayList<Mark> field = new ArrayList<>();

    public MarksList(String id, ArrayList<Mark> field) {
        this.id = id;
        this.field = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Mark> getField() {
        return field;
    }

    public void setField(ArrayList<Mark> field) {
        this.field = field;
    }

    public static String calcId(Long courseId, Long studentId){
        return courseId + "_" + studentId;
    }
}