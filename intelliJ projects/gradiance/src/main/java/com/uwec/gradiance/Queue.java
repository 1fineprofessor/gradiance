package com.uwec.gradiance;

import ch.qos.logback.core.net.QueueFactory;
import lombok.Getter;

import java.util.LinkedList;

public class Queue {
    //members
    private LinkedList<QueueNode> queueSelf;
    @Getter
    private String ID;
    //constructor
    Queue(){
        this.queueSelf = new LinkedList<QueueNode>();
        this.ID = null; //where are we deriving a queue's ID from?
    }

    //methods

    //iterate through list
    //update each student's priority based on a passed in target(course or evaluation)
    public void updatePriority(String priorityTarget){ //accepts a value corresponding to either a course or evaluation
        QueueNode currentOutput;
        if(queueSelf.getFirst() == null) return;
        while(queueSelf.iterator().hasNext()){
            currentOutput = queueSelf.iterator().next();
            if(currentOutput.getEvaluation() == priorityTarget || currentOutput.getCourse() == priorityTarget){
                currentOutput.setPriority(1);
            } else if(currentOutput.getPriority() >= 1) currentOutput.setPriority(-1);
        }
    }
    //find the next student who should be called in by default
    //then return the node
    //does not yet factor in joinTime
    public QueueNode findNext(){
        QueueNode currentOutput;
        QueueNode nextOutput;
        if(queueSelf.getFirst() == null) return null;
        currentOutput = queueSelf.getFirst();
        while(queueSelf.iterator().hasNext()){
            nextOutput = queueSelf.iterator().next();
            int nextCheckPriority = nextOutput.getPriority();
            if (currentOutput.getPriority() < nextCheckPriority) {
                currentOutput = nextOutput;
            }
        }
        return currentOutput;
    }
    //find a student by some form of primary key(currently set to email preemptively)
    //then return the node
    public QueueNode findStudent(String email){
        QueueNode targetStudent = null;
        return targetStudent;
    }
    public void callStudent(){

    }
    //append a student. currently returns nothing
    public void appendStudent(QueueNode newStudent){
        queueSelf.add(newStudent);
    }
}
