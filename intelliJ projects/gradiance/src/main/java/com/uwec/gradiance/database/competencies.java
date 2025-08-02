package com.uwec.gradiance.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "competencies")
public class competencies {
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "competency_id", nullable = false)
    @Getter
    private Long competency_id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "course_id")
    @Getter
    @Setter
    private courses course_id;

    @Column (name = "competency_name", length = 100, nullable = false)
    @Getter
    @Setter
    private String competency_name;

    @Column (name = "description")
    @Getter
    @Setter
    private String description;

    @Column (name = "semester")
    @Getter
    @Setter
    private String semester;

    //constructors
    public competencies(String competency_name){
        setCompetency_name(competency_name);
    }
    public competencies(String competency_name, String description){
        setCompetency_name(competency_name);
        setDescription(description);
    }
    public competencies(String competency_name, String description, String semester){
        setCompetency_name(competency_name);
        setDescription(description);
        setSemester(semester);
    }

    //methods
}
