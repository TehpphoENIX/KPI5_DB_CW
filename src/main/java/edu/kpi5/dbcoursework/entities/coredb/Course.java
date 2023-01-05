package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="COURSE")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="course_name", length=50, nullable=false, unique=false)
	private String name;
	@OneToMany(mappedBy = "course")
	private Set<StudentCourseMarks> students = new HashSet<>();

	//private ArrayList<AbstractMap.SimpleEntry<String, ArrayList
	//		<AbstractMap.SimpleEntry<String, Float>>>> field;

	public Course() {
	}

	public Course(String name) {
		this.name = name;
	}

//	public Course(String name, ArrayList<AbstractMap.SimpleEntry<String,
//			ArrayList<AbstractMap.SimpleEntry<String, Float>>>> field) {
//		this.name = name;
//
//	//	this.field = field;
//	}
	public String getName() {

		return name;
	}

	//public ArrayList<AbstractMap.SimpleEntry<String,
	//		ArrayList<AbstractMap.SimpleEntry<String, Float>>>> getField() {
	//	return field;
	//}
	public void setName(String name) {

		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<StudentCourseMarks> getStudents() {
		return students;
	}

	public void setStudents(Set<StudentCourseMarks> students) {
		this.students = students;
	}

	//public void setField(ArrayList<AbstractMap.SimpleEntry<String,
	//		ArrayList<AbstractMap.SimpleEntry<String, Float>>>> field) {
	//	this.field = field;
	//}

	@Override
	public String toString() {

		return "Course{" +
				"name='" + name + '\'' +
				//", field=" + field +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Course course = (Course) o;
		return Objects.equals(id, course.id);
	}
	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
