package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.coredb.Course;
import edu.kpi5.dbcoursework.entities.coredb.Student;
import edu.kpi5.dbcoursework.entities.coredb.User;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;

import java.util.ArrayList;

public class TeacherHandle extends Handle {
    public TeacherHandle(User user) {
        super(user);
    }

    //UNREALIZED
//    public void setAttestation(String courseName, MarksList marksList, DBApi object) {
//        //потрібно знати за яким правилом виставлятиметься атестація, наприклад якщо більше 20 балів
//        //але за що? ключ в марксЛісті містить тільки назву роботи, тому ми будемо тільки враховуючи її
//        // виставляти атестацію ?
//        for(var pair : marksList.getList()){//отримуємо список пар учень - оцінка
//            if(pair.getValue() > 20){
//                object.setAttestation(courseName,pair.getKey());//треба додати метод до BDApi для виставлення атестації учню за ім'ям
//            }
//        }
//    }

    public void setMark(Long courseId, Student Student, int mark, DBApi object) {
        try{
            //переписати, бо непевний чи правильно розташував ключі
            object.setMarks(courseId, null);
        }catch (Exception e){
            //error handling
        }
    }//todo

    public void setMarks(Long courseId, MarksList marksList, DBApi object) {
        try{
            object.setMarks(courseId, marksList);
        }catch (Exception e){
            //error handling
        }
    }

    public void setSocialWork(Long courseId, Long studentId, DBApi object) {
        try{
            object.setSocialWork(courseId, studentId);
        }catch (Exception e){
            //error handling
        }
    }

    public void setExam(Long courseId, MarksList marksList, DBApi object) {
        try{
            object.setExam(courseId, marksList);
        }catch (Exception e){
            //error handling
        }
    }

    public void addCourse(String courseName, ArrayList<String> groups,DBApi object) {
        try{
            object.addCourse(courseName);
        }catch (Exception e){
            //error handling
        }
    }//todo

    public void editCourse(Course course, DBApi object) {
        try{
            object.editCourse(course);
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

    public ArrayList<Student> getCourseStudents(Course course, DBApi object) {
        return new ArrayList<>(object.getCourseStudents(course));
    }

    public ArrayList<Course> getCourseList(DBApi object) {
        return new ArrayList<>(object.getCourseList(super.getUser()));
    }
}
