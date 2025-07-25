package com.uwec.gradiance.service;

import org.springframework.stereotype.Service;
import com.uwec.gradiance.repository.UserRepository;
import com.uwec.gradiance.database.Users;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    /**
     * Constructor for UserService.
     *
     * @param repo    The UserRepository for database operations.
     * @param encoder The PasswordEncoder (e.g., BCrypt) for hashing passwords.
     */
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    /**
     * Registers a new user by taking the client's SHA-256 hashed password,
     * encoding it again using the configured PasswordEncoder (for added security),
     * and saving the resulting bcrypt hash in the database.
     *
     * @param email      The user's email address (used as login ID).
     * @param clientSha  The SHA-256 hash generated on the client-side.
     */
    public void register(String email, String clientSha) {
        String finalHash = encoder.encode(clientSha);
        repo.save(new Users(email, finalHash));
    }

    /**
     * Validates user login attempt by comparing the provided client-side SHA-256 hash
     * of the password against the stored hash in the database.
     *
     * @param email      The user's email address.
     * @param clientSha  The SHA-256 hash from the login request.
     * @return boolean   True if credentials match, false otherwise.
     */
    public boolean login(String email, String clientSha) {
        return repo.findByEmail(email)
                   .map(user -> encoder.matches(clientSha, user.getPassword()))
                   .orElse(false);
    }
}