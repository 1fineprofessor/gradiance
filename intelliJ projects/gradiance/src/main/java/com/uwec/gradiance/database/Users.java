package com.uwec.gradiance.database;

import com.uwec.gradiance.model.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "Users") // Case-sensitive for H2!
public class Users {

    public Users() {
        // Default constructor for JPA
    }

    public Users(String email, String finalHash) {
        this.email = email;
        this.password = finalHash;
        
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "student_id", unique = true, length = 20)
    private String studentId;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "middle_initial", length = 1)
    private String middleInitial;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleEnum role;

    @Column(name = "admin_rights", nullable = false)
    private int adminRights;

    // === Getters and Setters ===

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getStudentId() { return studentId; }

    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleInitial() { return middleInitial; }

    public void setMiddleInitial(String middleInitial) { this.middleInitial = middleInitial; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public RoleEnum getRole() { return role; }

    public void setRole(RoleEnum role) { this.role = role; }

    public int getAdminRights() { return adminRights; }

    public void setAdminRights(int adminRights) { this.adminRights = adminRights; }
}
