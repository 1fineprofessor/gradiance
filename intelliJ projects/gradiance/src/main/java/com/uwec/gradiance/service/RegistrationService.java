package com.uwec.gradiance.service;

import com.uwec.gradiance.dao.GeneralDao;
import com.uwec.gradiance.database.Users;
import com.uwec.gradiance.model.RegistrationRequest;
import com.uwec.gradiance.model.RoleEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class RegistrationService {
    private final GeneralDao dao;
    private final PasswordEncoder encoder;

    public RegistrationService(GeneralDao dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    public void register(RegistrationRequest req, BindingResult errors) {
        // 1. Check studentId uniqueness
        if (dao.existsByStudentId(req.getStudentId())) {
            errors.rejectValue("studentId", null, "Student ID already taken");
        }

        // 2. Check email uniqueness
        if (dao.findUserByEmail(req.getEmail()).isPresent()) {
            errors.rejectValue("email", null, "Email already registered");
        }

        // 3. Check password match
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", null, "Passwords do not match");
        }

        if (errors.hasErrors()) {
            return; // abort on validation errors
        }

        // 4. Build and save user entity
        Users user = new Users();
        user.setStudentId(req.getStudentId());
        user.setFirstName(req.getFirstName());
        user.setMiddleInitial(req.getMiddleInitial());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(RoleEnum.STUDENT);  // set RoleEnum directly
        user.setAdminRights(0);


        dao.save(user);
    }
}
