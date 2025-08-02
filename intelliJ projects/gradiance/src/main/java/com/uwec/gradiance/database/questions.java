package com.uwec.gradiance.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
public class questions {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "question_id", nullable = false)
    @Getter
    private Long question_id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "parent_question_id", referencedColumnName = "question_id")
    @Getter
    @Setter
    private questions parent_question_id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "created_by", referencedColumnName ="user_id", nullable = false)
    @Getter
    @Setter
    private Users created_by;

    @Column (name = "created_at")
    @Getter
    @Setter
    private Timestamp created_at;

    @Column (name = "last_modified_at")
    @Getter
    @Setter
    private Timestamp last_modified_at;

    @Column (name = "question_text", nullable = false)
    @Getter
    @Setter
    private String question_text;

    @Column (name = "question_image_url", length = 100)
    @Getter
    @Setter
    private String question_image_url;

    //constructors
    public questions(String question_text, Users created_by){
        setQuestion_text(question_text);
        setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        setLast_modified_at(Timestamp.valueOf(LocalDateTime.now()));
        setCreated_by(created_by);
    }
    public questions(String question_text, String question_image_url, Users created_by){
        setQuestion_text(question_text);
        setQuestion_image_url(question_image_url);
        setCreated_by(created_by);
        setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        setLast_modified_at(Timestamp.valueOf(LocalDateTime.now()));
    }
    public questions(String question_text, questions parent_question_id, Users created_by){
        setQuestion_text(question_text);
        setParent_question_id(parent_question_id);
        setCreated_by(created_by);
        setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        setLast_modified_at(Timestamp.valueOf(LocalDateTime.now()));
    }
    public questions(String question_text, String question_image_url, questions parent_question_id, Users created_by){
        setQuestion_text(question_text);
        setQuestion_image_url(question_image_url);
        setParent_question_id(parent_question_id);
        setCreated_by(created_by);
        setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        setLast_modified_at(Timestamp.valueOf(LocalDateTime.now()));
    }
    //methods
    public void updateTimestamp(){
        setLast_modified_at(Timestamp.valueOf(LocalDateTime.now()));
    }
}
