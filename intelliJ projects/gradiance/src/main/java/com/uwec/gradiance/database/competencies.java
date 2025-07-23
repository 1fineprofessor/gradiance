package com.uwec.gradiance.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class competencies {
    @Id
    @GeneratedValue
    private Long id;

}
