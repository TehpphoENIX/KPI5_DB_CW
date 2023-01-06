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
	private Set<StudentCourseMarks> marks;
	@ManyToMany
	@JoinTable(name = "teacher_course",
		joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"))
	private Set<Teacher> teachers = new HashSet<>();
	public Course() {
	}
	public Course(String name) {
		this.name = name;
	}
	public String getName() {

		return name;
	}
	public void setName(String name) {

		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<StudentCourseMarks> getMarks() {
		return marks;
	}

	public void setMarks(Set<StudentCourseMarks> marks) {
		this.marks = marks;
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

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
