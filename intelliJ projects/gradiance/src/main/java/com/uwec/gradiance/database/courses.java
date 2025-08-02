package com.uwec.gradiance.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courses")
public class courses {
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name ="course_id")
    @Getter
    private Long course_id;

    @Column (name = "course_name", length = 100)
    @Getter
    @Setter
    private String course_name;

    @Column (name = "pre_reqs")
    @Getter
    @Setter
    private String pre_reqs;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "created_by", referencedColumnName = "user_id")
    @Getter
    @Setter
    private Users created_by;

    //constructors

    public courses(String course_name, Users created_by){
        setCourse_name(course_name);
        setCreated_by(created_by);
    }
    public courses(String course_name, Users created_by, String pre_reqs){
        setCourse_name(course_name);
        setCreated_by(created_by);
        setPre_reqs(pre_reqs);
    }
    //methods
}
