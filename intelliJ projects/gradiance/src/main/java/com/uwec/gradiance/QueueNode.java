package com.uwec.gradiance;

import lombok.Getter;

public class QueueNode {

    //members
        @Getter
        private String email;
        @Getter
        private String course;
        @Getter
        private String evaluation;
        @Getter
        private long joinTime;
        @Getter
        private int priority;
    //constructor
    QueueNode(String email, String course, String evaluation){
        this.email = email;
        this.course = course;
        this.evaluation = evaluation;
        this.joinTime = System.currentTimeMillis();
        this.priority = 0;
    }
    public void setPriority(int mod){
        this.priority += mod;
    }
}
