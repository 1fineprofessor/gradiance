package com.uwec.gradiance.repository;

import com.uwec.gradiance.database.questions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Question} entities.
 */
public interface QuestionRepository extends JpaRepository<questions, Long> {

}