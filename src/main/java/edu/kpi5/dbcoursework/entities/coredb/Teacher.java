package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name  = "TEACHER")
public class Teacher {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	//@OneToOne
	@JoinColumn(name = "teacher_login")
	private String login;

	@Column(name = "teacher_name", length = 50, nullable = false)
	private String name;

	@Column(name = "teacher_surname", length = 50, nullable = false)
	private String surname;

	@ManyToOne
	@JoinColumn(name="teacher_department")
	private Department department;

	@ManyToMany(mappedBy = "teachers")
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

		/*this.contribution = contribution;*/
	}

	public Long getId() {

		return id;
	}

	public String getLogin() {

		return login;
	}

	public String getName() {

		return name;
	}

	public String getSurname() {

		return surname;
	}

	public Department getDepartment() {

		return department;
	}

	public Set<Course> getCourses() {

		return courses;
	}

	/*public ArrayList<AbstractMap.SimpleEntry<Float, Float>> getContribution() {

		return contribution;
	}*/

	public void setId(Long id) {

		this.id = id;
	}

	public void setLogin(String login) {

		this.login = login;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setSurname(String surname) {

		this.surname = surname;
	}

	public void setDepartment(Department department) {

		this.department = department;
	}
	public void setCourses(Set<Course> courses) {

		this.courses = courses;
	}


	/*public void setContribution(ArrayList<AbstractMap.SimpleEntry<Float, Float>> contribution) {

		this.contribution = contribution;
	}*/

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
