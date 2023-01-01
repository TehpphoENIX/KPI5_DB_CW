package edu.kpi5.dbcoursework.entities;

import java.util.Objects;

public class User {

    private String login;

    private AccessLevel accessLevel;

    public User() {}

    public User(String login, AccessLevel accesslevel) {

        this.login = login;

        this.accessLevel = accesslevel;
    }

    public String getLogin() {

        return login;
    }

    public AccessLevel getAccessLevel() {

        return accessLevel;
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
                "login='" + login + '\'' +
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

        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
