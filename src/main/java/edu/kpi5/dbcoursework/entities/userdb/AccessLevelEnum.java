package edu.kpi5.dbcoursework.entities.userdb;

public enum AccessLevelEnum {
    none(""),
    student("student"),
    teacher("teacher"),
    admin("admin");

    public final String label;
    AccessLevelEnum(String label) {
        this.label = label;
    }
}
