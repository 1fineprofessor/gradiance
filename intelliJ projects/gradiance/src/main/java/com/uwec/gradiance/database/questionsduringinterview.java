package com.uwec.gradiance.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class questionsduringinterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
