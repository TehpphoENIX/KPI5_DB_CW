package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "student_name", length = 50, nullable = false)
	private String name;
	@Column(name = "student_surname", length = 50, nullable = false)
	private String surname;
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	@Column(name = "student_hostel", nullable = false)
	private Integer hostel;
	@Column(name = "student_noe")
	private Short numberOfExams;
	@Column(name = "student_avg")
	private Float averageMark;
	@Column(name = "student_sw")
	private Boolean socialWork;
	@OneToMany(mappedBy = "student")
	private Set<StudentCourseMarks> courses = new HashSet<>();

	public Student() {
	}

	public Student(String name, String surname, Group group, Integer hostel,
	               Short numberOfExams, Float averageMark, Boolean socialWork) {
		this.name = name;
		this.surname = surname;
		this.group = group;
		this.hostel = hostel;
		this.numberOfExams = numberOfExams;
		this.averageMark = averageMark;
		this.socialWork = socialWork;
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public Group getGroup() {
		return group;
	}
	public Integer getHostel() {
		return hostel;
	}
	public Short getNumberOfExams() {
		return numberOfExams;
	}
	public Float getAverageMark() {
		return averageMark;
	}
	public Boolean getSocialWork() {
		return socialWork;
	}
	public Set<StudentCourseMarks> getCourses() {
		return courses;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public void setHostel(Integer hostel) {
		this.hostel = hostel;
	}
	public void setNumberOfExams(Short numberOfExams) {
		this.numberOfExams = numberOfExams;
	}
	public void setAverageMark(Float averageMark) {
		this.averageMark = averageMark;
	}
	public void setSocialWork(Boolean socialWork) {
		this.socialWork = socialWork;
	}
	public void setCourses(Set<StudentCourseMarks> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {

		return "Student{" +
				"ID=" + id +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", group=" + group +
				", hostel=" + hostel +
				", numberOfExams=" + numberOfExams +
				", averageMark=" + averageMark +
				", socialWork=" + socialWork +
				'}';
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Student student = (Student) o;

		return id.equals(student.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
