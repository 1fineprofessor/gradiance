package com.uwec.gradiance.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "questionsduringinterview")
public class questionsduringinterview {
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long assignment_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id",referencedColumnName = "interview_id", nullable = false)
    @Getter
    @Setter
    private interviews interview_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_id", referencedColumnName = "usage_id", nullable = false)
    @Getter
    @Setter
    private questionusages usage_id;

    //constructors
    public questionsduringinterview(interviews interview_id, questionusages usage_id){
        setInterview_id(interview_id);
        setUsage_id(usage_id);
    }

    //methods
}
