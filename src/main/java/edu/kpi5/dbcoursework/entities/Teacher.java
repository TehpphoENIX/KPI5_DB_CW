package edu.kpi5.dbcoursework.entities;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Objects;

public class Teacher {

	private String name;

	private String surname;

	private ArrayList<AbstractMap.SimpleEntry<Float, Float>> contribution;

	public Teacher() {
	}

	public Teacher(String login, String name, String surname, ArrayList<AbstractMap.SimpleEntry<Float, Float>> contribution) {

		this.name = name;

		this.surname = surname;

		this.contribution = contribution;
	}

	public String getName() {

		return name;
	}

	public String getSurname() {

		return surname;
	}

	public ArrayList<AbstractMap.SimpleEntry<Float, Float>> getContribution() {

		return contribution;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setSurname(String surname) {

		this.surname = surname;
	}

	public void setContribution(ArrayList<AbstractMap.SimpleEntry<Float, Float>> contribution) {

		this.contribution = contribution;
	}

	@Override
	public String toString() {

		return "Teacher{" +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", contribution=" + contribution +
				'}';
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Teacher teacher = (Teacher) o;

		return name.equals(teacher.name) && surname.equals(teacher.surname);
	}

	@Override
	public int hashCode() {

		return Objects.hash(name, surname);
	}
}
