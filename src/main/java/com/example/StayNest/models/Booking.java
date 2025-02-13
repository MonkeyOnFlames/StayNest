package com.example.StayNest.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collections;

@Document(collection = "bookings")
public class Booking {
@Id
private String id;

@NotEmpty(message = "Name can not be empty")
@NotNull(message = "Name can not be null")
private String name;

@NotEmpty(message = "Description can not be empty")
@NotNull(message = "Description can not be null")
private String description;

@NotEmpty(message = "Date can not be empty")
@NotNull(message = "Date can not be null")
@Positive(message = "Date must be grater than zero")
@PositiveOrZero(message = "Date can not be negative number")
@DateTimeFormat
private double date;
@NotEmpty(message = "Available can not be empty")
@NotNull(message = "Available can not be null")
private boolean available;

    public Booking() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public @NotEmpty(message = "Description can not be empty") @NotNull(message = "Description can not be null") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Description can not be empty") @NotNull(message = "Description can not be null") String description) {
        this.description = description;
    }

    @NotEmpty(message = "Date can not be empty")
    @NotNull(message = "Date can not be null")
    @Positive(message = "Date must be grater than zero")
    @PositiveOrZero(message = "Date can not be negative number")
    public double getDate() {
        return date;
    }

    public void setDate(@NotEmpty(message = "Date can not be empty") @NotNull(message = "Date can not be null") @Positive(message = "Date must be grater than zero") @PositiveOrZero(message = "Date can not be negative number") double date) {
        this.date = date;
    }

    @NotEmpty(message = "Available can not be empty")
    @NotNull(message = "Available can not be null")
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(@NotEmpty(message = "Available can not be empty") @NotNull(message = "Available can not be null") boolean available) {
        this.available = available;
    }

    public @NotEmpty(message = "Name can not be empty") @NotNull(message = "Name can not be null") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name can not be empty") @NotNull(message = "Name can not be null") String name) {
        this.name = name;
    }
}
