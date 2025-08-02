package com.uwec.gradiance.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
public class interviews {
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long interview_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @Getter
    @Setter
    private Users student_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @Getter
    @Setter
    private courses course_id;

    @Column (name="conducted_at")
    @Getter
    @Setter
    private Timestamp conducted_at;

    //constructors
    public interviews(Users student_id, courses course_id){
        setStudent_id(student_id);
        setCourse_id(course_id);
        setConducted_at(Timestamp.valueOf(LocalDateTime.now()));
    }

    //methods
}
