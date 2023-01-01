package edu.kpi5.dbcoursework.entities;

import java.util.Objects;

public class Student extends User{

	private Long ID;

	private String name;

	private String surname;

	private Group group;

	private Integer hostel;

	private Short numberOfExams;

	private Float averageMark;

	private Boolean socialWork;

	public Student() {
	}

	public Student(String login, Long ID,
	               String name, String surname, Group group, Integer hostel,
	               Short numberOfExams, Float averageMark, Boolean socialWork) {

		super(login, AccessLevel.student);

		this.ID = ID;

		this.name = name;

		this.surname = surname;

		this.group = group;

		this.hostel = hostel;

		this.numberOfExams = numberOfExams;

		this.averageMark = averageMark;

		this.socialWork = socialWork;
	}

	public Long getID() {

		return ID;
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

	public void setID(Long ID) {

		this.ID = ID;
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

	@Override
	public String toString() {

		return "Student{" +
				"ID=" + ID +
				", login='" + getLogin() + '\'' +
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

		return ID.equals(student.ID);
	}

	@Override
	public int hashCode() {

		return Objects.hash(ID);
	}
}
