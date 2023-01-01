package edu.kpi5.dbcoursework.entities;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Objects;

public class Course {

	private String name;

	private ArrayList<AbstractMap.SimpleEntry<String, ArrayList
			<AbstractMap.SimpleEntry<String, Float>>>> field;

	public Course() {
	}

	public Course(String name, ArrayList<AbstractMap.SimpleEntry<String,
			ArrayList<AbstractMap.SimpleEntry<String, Float>>>> field) {

		this.name = name;

		this.field = field;
	}

	public String getName() {

		return name;
	}

	public ArrayList<AbstractMap.SimpleEntry<String,
			ArrayList<AbstractMap.SimpleEntry<String, Float>>>> getField() {

		return field;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setField(ArrayList<AbstractMap.SimpleEntry<String,
			ArrayList<AbstractMap.SimpleEntry<String, Float>>>> field) {

		this.field = field;
	}

	@Override
	public String toString() {

		return "Course{" +
				"name='" + name + '\'' +
				", field=" + field +
				'}';
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Course course = (Course) o;

		return name.equals(course.name);
	}
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
