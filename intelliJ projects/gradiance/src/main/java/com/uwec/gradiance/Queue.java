package com.uwec.gradiance;

import java.util.LinkedList;

public class Queue {
    //members
    private LinkedList queueSelf;
    private String ID;
    //constructor
    Queue(){
        this.queueSelf = new LinkedList();
        this.ID = null; //where are we deriving a queue's ID from?
    }

    //methods
    public void updatePriority(String priorityTarget){ //accepts a value corresponding to either a course or evaluation

    }
    public QueueNode findNext(String email){
        QueueNode nextStudent = null;
        return nextStudent;
    }
    public QueueNode findStudent(String email){
        QueueNode targetStudent = null;
        return targetStudent;
    }
    public void callStudent(){

    }
    public String getID(){
        return this.ID;
    }
}
