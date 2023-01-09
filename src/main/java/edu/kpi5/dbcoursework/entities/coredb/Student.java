package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@Getter
@Setter
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@OneToOne
	@Column(name = "student_login", unique = true)
	private String login;

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

	@OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
	private Set<StudentCourseMarks> marks = new HashSet<>();

	@Column(name = "scholarship")
	private Integer scholarship;

	public Student() {
	}

	public Student(String login, String name, String surname, Group group,
	               Integer hostel, Short numberOfExams, Float averageMark,
	               Boolean socialWork) {
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.group = group;
		this.hostel = hostel;
		this.numberOfExams = numberOfExams;
		this.averageMark = averageMark;
		this.socialWork = socialWork;
	}

	@Override
	public String toString() {

		return "Student{" +
				"ID=" + id +
				", login='" + login + '\'' +
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
