package com.uwec.gradiance.model;

import jakarta.validation.constraints.*;

public class RegistrationRequest {

    @NotBlank
    @Size(max = 20)
    private String studentId;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Size(max = 1)
    @Pattern(regexp = "[A-Za-z]?", message = "Middle initial must be a single letter")
    private String middleInitial;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be ≥8 characters")
    private String password;

    @NotBlank
    private String confirmPassword;


    // Getters & Setters

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
