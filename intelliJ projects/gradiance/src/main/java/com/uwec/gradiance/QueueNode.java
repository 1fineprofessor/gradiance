package com.uwec.gradiance;

public class QueueNode {

    //members
        private String email;
        private String course;
        private String evaluation;
        private long joinTime;
        private int priority;
    //constructor
    QueueNode(String email, String course, String evaluation){
        this.email = email;
        this.course = course;
        this.evaluation = evaluation;
        this.joinTime = System.currentTimeMillis();
        this.priority = 0;
    }
}
