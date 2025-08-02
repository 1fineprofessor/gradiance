package com.uwec.gradiance.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "questionusages")
public class questionusages {
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long usage_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName ="question_id", nullable = false)
    @Getter
    @Setter
    private questions question_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competency_id", referencedColumnName = "competency_id")
    @Getter
    @Setter
    private competencies competency_id;

    //constructors
    public questionusages(questions question_id, competencies competency_id){
        setQuestion_id(question_id);
        setCompetency_id(competency_id);
    }

    //methods
}
