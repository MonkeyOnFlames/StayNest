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

    //@NotBlank vet inte om vi ska ha denna
    private String email;
    private String firstName;
    private String lastName;
    private String adress;
    private String phone;
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

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAdress() {
        return adress;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getAge() {
        return age;
    }
}
