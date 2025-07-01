package com.Demo.webAppDemo2.demo.model;

public class Student {
    private String name;
    private String studentClass;
    private String competency;


    public Student(String name, String studentClass, String competency) {
        this.name = name;
        this.studentClass = studentClass;
        this.competency = competency;
    }

    public String getName() {
        return name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getCompetency() {
        return competency;
    }
}

