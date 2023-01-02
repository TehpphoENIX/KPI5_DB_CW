package edu.kpi5.dbcoursework.entities;

import java.util.Objects;

public class Group {

	private String name;

	private Integer yearCreated;

	private Short speciality;

	public Group() {
	}

	public Group(String name, Integer yearCreated, Short speciality) {

		this.name = name;

		this.yearCreated = yearCreated;

		this.speciality = speciality;
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

	public void setName(String name) {

		this.name = name;
	}

	public void setYearCreated(Integer yearCreated) {

		this.yearCreated = yearCreated;
	}

	public void setSpeciality(Short speciality) {

		this.speciality = speciality;
	}

	@Override
	public String toString() {

		return "Group{" +
				"name='" + name + '\'' +
				", yearCreated=" + yearCreated +
				", speciality=" + speciality +
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

		return Objects.hash(name, yearCreated, speciality);
	}
}
