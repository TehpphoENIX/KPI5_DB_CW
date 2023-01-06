package edu.kpi5.dbcoursework.entities.userdb;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Objects;

@Node("AccessLevel")
public class AccessLevel {

	@Id
	private String name;

	public AccessLevel() {
	}

	public AccessLevel(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public String toString() {

		return "AccessLevel{" +
				"name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		AccessLevel that = (AccessLevel) o;

		return name.equals(that.name);
	}

	@Override
	public int hashCode() {

		return name != null ? name.hashCode() : 0;
	}
}
