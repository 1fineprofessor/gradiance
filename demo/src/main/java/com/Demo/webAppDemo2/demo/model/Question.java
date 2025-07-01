package com.Demo.webAppDemo2.demo.model;

public class Question {
    private String questionText;
    private String questionId;
    private String difficulty;
    private String category;
    private String avgTime;

    public Question(String questionText, String questionId, String difficulty, String category, String avgTime) {
        this.questionText = questionText;
        this.questionId = questionId;
        this.difficulty = difficulty;
        this.category = category;
        this.avgTime = avgTime;
    }


    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }

    public String getAvgTime() {
        return avgTime;
    }
}
