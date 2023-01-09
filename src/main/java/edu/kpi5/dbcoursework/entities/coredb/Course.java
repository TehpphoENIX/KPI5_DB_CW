package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name="COURSE")
@Getter
@Setter
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="course_name", length=50, nullable=false, unique=false)
	private String name;
	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
	private Set<StudentCourseMarks> marks = new HashSet<>();
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "teacher_course",
		joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"))
	private Set<Teacher> teachers = new HashSet<>();
	public Course() {
	}
	public Course(String name) {
		this.name = name;
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
