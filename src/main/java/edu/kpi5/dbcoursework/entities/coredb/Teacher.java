package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name  = "TEACHER")
@Getter
@Setter
public class Teacher {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	//@OneToOne
	@Column(name = "teacher_login", unique = true)
	private String login;

	@Column(name = "teacher_name", length = 50, nullable = false)
	private String name;

	@Column(name = "teacher_surname", length = 50, nullable = false)
	private String surname;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="teacher_department")
	private Department department;

	@ManyToMany(mappedBy = "teachers", fetch = FetchType.EAGER)
	private Set<Course> courses = new HashSet<>();

	/*private ArrayList<AbstractMap.SimpleEntry<Float, Float>> contribution;*/

	public Teacher() {
	}

	public Teacher(String login, String name, String surname, Department department
			/*, ArrayList<AbstractMap.SimpleEntry<Float, Float>> contribution*/) {

		this.login = login;

		this.name = name;

		this.surname = surname;

		this.department = department;
	}

	@Override
	public String toString() {
		return "Teacher{" +
				"id=" + id +
				", login='" + login + '\'' +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", department='" + department.getName() + '\'' +
				/*", contribution=" + contribution +*/
				'}';
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Teacher teacher = (Teacher) o;

		return login.equals(teacher.login)
				&& id.equals(teacher.id);
	}

	@Override
	public int hashCode() {

		return id != null ? id.hashCode() : 0;
	}
}
