package com.uwec.gradiance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TestQueueNode {
    private String email;
    private String name; // or any other fields you want
    private String questionText;
    private String studentClass;
    private String competency;

    public TestQueueNode() {}

    public TestQueueNode(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public TestQueueNode(String email, String name, String studentClass, String competency) {
        this.email = email;
        this.name = name;
        this.studentClass = studentClass;
        this.competency = competency;
    }

    public void setQuestionId(String questionText) {
        this.questionText = questionText;
    }
}
