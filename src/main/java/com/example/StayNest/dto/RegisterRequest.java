package com.example.StayNest.dto;

import com.example.StayNest.models.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Set<Role> roles;

    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String adress;
    @NotBlank
    private String phone;
    @NotBlank
    private Integer age;

    public RegisterRequest(String username, String password, Set<Role> roles, String email, String firstName, String lastName, String adress, String phone, Integer age) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.phone = phone;
        this.age = age;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public @NotBlank String getAdress() {
        return adress;
    }

    public @NotBlank String getPhone() {
        return phone;
    }

    public @NotBlank Integer getAge() {
        return age;
    }
}
