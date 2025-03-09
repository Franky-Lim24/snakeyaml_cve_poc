package com.example.snakeyaml;

import java.util.*;

public class Student extends Person {
    private long id;
    private Object department;
    private List<Course> courses;

    // Getters and setters
    public void setId(int newId) {
        this.id = newId;
    }

    public void setDepartment(Object department) {
        System.out.println("Received department type: " + department.getClass().getName());
        this.department = department;
    }


    public void setCourses(List<Course> newCourses) {
        this.courses = newCourses;
    }

    public long getId() {
        return id;
    }

    public Object getDepartment() {
        return department;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
