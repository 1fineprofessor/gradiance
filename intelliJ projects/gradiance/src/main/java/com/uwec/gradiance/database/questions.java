package com.uwec.gradiance.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class questions {
    @Id
    @GeneratedValue
    private Long id;
}
