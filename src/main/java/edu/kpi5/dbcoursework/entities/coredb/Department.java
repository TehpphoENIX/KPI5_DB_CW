package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;

import java.util.Set;

public class Department {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column(name = "department_name", length = 50, nullable = false)
	private String name;

	@OneToMany(mappedBy = "department")
	private Set<Group> groups;

	@OneToMany(mappedBy = "department")
	private Set<Teacher> teachers;

	public Department() {
	}

	public Department(String name) {

		this.name = name;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public Set<Group> getGroups() {

		return groups;
	}

	public Set<Teacher> getTeachers() {

		return teachers;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setGroups(Set<Group> groups) {

		this.groups = groups;
	}

	public void setTeachers(Set<Teacher> teachers) {

		this.teachers = teachers;
	}

	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", name='" + name + '\'' +
				", groups=" + groups +
				", teachers=" + teachers +
				'}';
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Department that = (Department) o;
		return id.equals(that.id) && name.equals(that.name);
	}

	@Override
	public int hashCode() {

		return id != null ? id.hashCode() : 0;
	}
}
