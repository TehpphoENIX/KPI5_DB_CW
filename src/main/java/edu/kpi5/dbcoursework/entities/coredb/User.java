package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne (mappedBy = "login")
    private String login;

    @Column(name = "access_level", length = 8, nullable = false)
    private AccessLevel accessLevel;

    public User() {}

    public User(String login, AccessLevel accesslevel) {

        this.login = login;

        this.accessLevel = accesslevel;
    }

    public Long getId() {

        return id;
    }

    public String getLogin() {

        return login;
    }

    public AccessLevel getAccessLevel() {

        return accessLevel;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public void setAccessLevel(AccessLevel accessLevel) {

        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accessLevel=" + accessLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        return login.equals(user.login)
                && id.equals(user.id);
    }

    @Override
    public int hashCode() {

        return id != null ? id.hashCode() : 0;
    }
}
