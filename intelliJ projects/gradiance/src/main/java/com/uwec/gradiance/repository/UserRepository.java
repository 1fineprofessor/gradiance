package com.uwec.gradiance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uwec.gradiance.model.User;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Extends Spring Data JPA’s {@link JpaRepository} to provide
 * CRUD methods and query capabilities without manual SQL.
 * Defines a custom finder {@code findByEmail(...)} for type-safe
 * lookups by email. Spring Data generates the implementation at runtime.
 * Used by {@link com.uwec.gradiance.config.UserService} for user persistence
 * and authentication flows.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}