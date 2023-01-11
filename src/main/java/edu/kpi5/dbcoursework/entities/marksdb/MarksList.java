package edu.kpi5.dbcoursework.entities.marksdb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Getter
@Setter
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Mark mark = (Mark) o;

            if (value != mark.value) return false;
            return key.equals(mark.key);
        }

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + value;
            return result;
        }
    }
    private ArrayList<Mark> field = new ArrayList<>();

    public MarksList() {
    }

    public MarksList(String id) {
        this.id = id;
    }

    public MarksList(String id, ArrayList<Mark> field) {
        this.id = id;
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarksList marksList = (MarksList) o;

        return id.equals(marksList.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static String calcId(Long courseId, Long studentId){
        return courseId + "_" + studentId;
    }
    public static double getTotal(MarksList list){
        var out = list.getField().stream().mapToDouble((Mark m)->m.value).average();
        if(out.isPresent()){
            return out.getAsDouble();
        }else{
            return 0.0;
        }
    }
}