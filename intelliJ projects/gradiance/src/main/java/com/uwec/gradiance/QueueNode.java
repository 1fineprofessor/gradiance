package com.uwec.gradiance;

import lombok.Getter;

public class QueueNode {

    //members
        private String email;
        private String course;
        private String evaluation;
        @Getter
        private long joinTime;
        @Getter
        private int priority;
        private
    //constructor
    QueueNode(String email, String course, String evaluation){
        this.email = email;
        this.course = course;
        this.evaluation = evaluation;
        this.joinTime = System.currentTimeMillis();
        this.priority = 0;
    }

}
