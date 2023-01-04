package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "FGROUP")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "group_name", length = 50, nullable = false)
	private String name;

	@Column(name = "group_year", nullable = false)
	private Integer yearCreated;

	@Column(name = "group_spec", nullable = false)
	private Short speciality;

	@ManyToOne
	@JoinColumn(name="group_department")
	private Department department;

	@OneToMany(mappedBy = "group")
	private Set<Student> Students;

	public Group() {
	}

	public Group(String name, Integer yearCreated,
	             Short speciality, Department department) {

		this.name = name;

		this.yearCreated = yearCreated;

		this.speciality = speciality;

		this.department = department;
	}

	public String getName() {

		return name;
	}

	public Integer getYearCreated() {

		return yearCreated;
	}

	public Short getSpeciality() {

		return speciality;
	}

	public Department getDepartment() {

		return department;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setYearCreated(Integer yearCreated) {

		this.yearCreated = yearCreated;
	}

	public void setSpeciality(Short speciality) {

		this.speciality = speciality;
	}

	public void setDepartment(Department department) {

		this.department = department;
	}

	@Override
	public String toString() {

		return "Group{" +
				"name='" + name + '\'' +
				", yearCreated=" + yearCreated +
				", speciality=" + speciality +
				", department=" + department.getName() +
				'}';
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Group group = (Group) o;

		return name.equals(group.name)
				&& yearCreated.equals(group.yearCreated)
				&& speciality.equals(group.speciality);
	}

	@Override
	public int hashCode() {

		return id != null ? id.hashCode() : 0;
	}
}
