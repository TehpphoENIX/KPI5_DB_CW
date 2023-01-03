package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.Student;
import edu.kpi5.dbcoursework.entities.User;
import edu.kpi5.dbcoursework.utility.MarksList;

import java.util.AbstractMap;
import java.util.ArrayList;

public class TeacherHandle extends Handle {
    public TeacherHandle(User user) {
        super(user);
    }

    public void setAttestation(String courseName, MarksList marksList, DBApi object) {
        //потрібно знати за яким правилом виставлятиметься атестація, наприклад якщо більше 20 балів
        //але за що? ключ в марксЛісті містить тільки назву роботи, тому ми будемо тільки враховуючи її
        // виставляти атестацію ?
        for(var pair : marksList.getList()){//отримуємо список пар учень - оцінка
            if(pair.getValue() > 20){
                object.setAttestation(courseName,pair.getKey());//треба додати метод до BDApi для виставлення атестації учню за ім'ям
            }
        }
    }

    public void setMark(String courseName, Student Student, int mark, DBApi object) {
        try{
            //переписати, бо непевний чи правильно розташував ключі
            object.setMarks(courseName, new MarksList(courseName, new AbstractMap.SimpleEntry<String, Float>(Student.getName(),Float.valueOf(mark))));
        }catch (Exception e){
            //error handling
        }
    }

    public void setMarks(String courseName, MarksList marksList, DBApi object) {
        try{
            object.setMarks(courseName, marksList);
        }catch (Exception e){
            //error handling
        }
    }

    public void setSocialWork(String courseName, MarksList marksList, DBApi object) {
        try{
            object.setSocialWork(courseName, marksList);
        }catch (Exception e){
            //error handling
        }
    }

    public void setExam(String courseName, MarksList marksList, DBApi object) {
        try{
            object.setExam(courseName, marksList);
        }catch (Exception e){
            //error handling
        }
    }

    public void addCourse(String courseName, ArrayList<String> groups,DBApi object) {
        try{
            object.addCourse(courseName,groups);
        }catch (Exception e){
            //error handling
        }
    }

    public void editCourse(String courseName, String newCourseName, DBApi object) {
        try{
            object.editCourse(courseName,newCourseName);
        }catch (Exception e){
            //error handling
        }
    }

    public void removeCourse(Long courseID, DBApi object) {
        //в класі курсу немає courseID
        //навіщо повертати булл ?
        try{
            object.removeCourse(courseID);
        }catch (Exception e){
            //error handling
        }
    }

    public Course getCourse(Long courseId, DBApi object) {
        return object.getCourse(courseId);
    }

    public ArrayList<Student> getCourseStudents(String courseName, DBApi object) {
        return new ArrayList<>(object.getCourseStudents(courseName));
    }

    public ArrayList<Course> getCourseList(DBApi object) {
        return new ArrayList<>(object.getCourseList(super.getUser()));
    }
}
