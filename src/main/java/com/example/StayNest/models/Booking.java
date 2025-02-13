package com.example.StayNest.models;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collections;
import java.util.Date;

@Document(collection = "bookings")
public class Booking {
@Id
private String id;

    public @NotNull(message = "Total amount can not be null") @PositiveOrZero(message = "Total amount can not be negative number") @Positive(message = "Total amount must be grater than zero") Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(@NotNull(message = "Total amount can not be null") @PositiveOrZero(message = "Total amount can not be negative number") @Positive(message = "Total amount must be grater than zero") Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    @NotNull(message = "Total amount can not be null")
    @PositiveOrZero(message = "Total amount can not be negative number")
    @Positive(message = "Total amount must be grater than zero")
private Double totalAmount;

@NotEmpty(message = "Description can not be empty")
@NotNull(message = "Description can not be null")
private String description;

    @CreatedDate
    private Date CreatedAt;
    @NotEmpty(message = "Available can not be empty")
    @NotNull(message = "Available can not be null")
    private boolean available;

public @NotEmpty(message = "Date can not be empty") @NotNull(message = "Date can not be null") @Positive(message = "Date must be grater than zero") @PositiveOrZero(message = "Date can not be negative number") Date getCreatedAt() {
        return CreatedAt;
    }



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



    @NotEmpty(message = "Available can not be empty")
    @NotNull(message = "Available can not be null")
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(@NotEmpty(message = "Available can not be empty") @NotNull(message = "Available can not be null") boolean available) {
        this.available = available;
    }


}
