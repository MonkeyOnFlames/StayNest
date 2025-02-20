package com.example.StayNest.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Indexed(unique = true)
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" +
                    ".*$",
            message = "Password must be at least 8 characters long and contain at least " +
                    "one uppercase letter, one number, and one special character"
    )
    private String password;

    private Set<Role> roles;

    @NotNull(message = "First name can't be null")
    @NotEmpty(message = "First name can't be empty")
    private String first_name;

    @NotNull(message = "Last name can't be null")
    @NotEmpty(message = "Last name can't be empty")
    private String last_name;

    //not sure if @NotEmpty works with Integer
//    @NotEmpty(message = "This can't be empty")
    @NotNull(message = "Can't be null")
    @Positive(message = "The age must be greater than 0")
    private Integer age;

    @NotNull(message = "Adress can't be null")
    @NotEmpty(message = "Adress can't be empty")
    private String adress;

    @NotNull(message = "The phone number can't be null")
    @NotEmpty(message = "The phone number can't be empty")
    private String phone;

    public Date getCreatedAt() {
        return createdAt;
    }

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public User() {
    }

    public User(String id, String username, String email, String password, Set<Role> roles, String first_name, String last_name, String adress, String phone, int age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.first_name = first_name;
        this.last_name = last_name;
        this.adress = adress;
        this.phone = phone;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public @NotEmpty(message = "Username cannot be empty") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username cannot be empty") String username) {
        this.username = username;
    }

    public @NotEmpty(message = "Email cannot be empty") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email cannot be empty") String email) {
        this.email = email;
    }

    public @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" +
                    ".*$",
            message = "Password must be at least 8 characters long and contain at least " +
                    "one uppercase letter, one number, and one special character"
    ) String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" +
                    ".*$",
            message = "Password must be at least 8 characters long and contain at least " +
                    "one uppercase letter, one number, and one special character"
    ) String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public @NotNull(message = "First name can't be null") @NotEmpty(message = "First name can't be empty") String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(@NotNull(message = "First name can't be null") @NotEmpty(message = "First name can't be empty") String first_name) {
        this.first_name = first_name;
    }

    public @NotNull(message = "Last name can't be null") @NotEmpty(message = "Last name can't be empty") String getLast_name() {
        return last_name;
    }

    public void setLast_name(@NotNull(message = "Last name can't be null") @NotEmpty(message = "Last name can't be empty") String last_name) {
        this.last_name = last_name;
    }

//    public @NotNull(message = "Can't be null") /*@NotEmpty(message = "This can't be empty")*/ Integer getAge() {
//        return age;
//    }
//
//    public void setAge(@NotNull(message = "Can't be null") /*@NotEmpty(message = "This can't be empty")*/ Integer age) {
//        this.age = age;
//    }

    public @NotNull(message = "Adress can't be null") @NotEmpty(message = "Adress can't be empty") String getAdress() {
        return adress;
    }

    public void setAdress(@NotNull(message = "Adress can't be null") @NotEmpty(message = "Adress can't be empty") String adress) {
        this.adress = adress;
    }

    public @NotNull(message = "The phone number can't be null") @NotEmpty(message = "The phone number can't be empty") String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull(message = "The phone number can't be null") @NotEmpty(message = "The phone number can't be empty") String phone) {
        this.phone = phone;
    }

    public @NotNull(message = "Can't be null") @Positive(message = "The age must be greater than 0") Integer getAge() {
        return age;
    }

    public void setAge(@NotNull(message = "Can't be null") @Positive(message = "The age must be greater than 0") Integer age) {
        this.age = age;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
