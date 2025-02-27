package com.example.StayNest.models;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collections;
import java.util.Date;

@Document(collection = "bookings")
public class Booking {
@Id
private String id;
@NotNull(message = "Listings ID can not me null")
@NotEmpty(message = "Listings ID can not be empty")
@DBRef
private String listingId;

@NotNull(message = "User ID can not be null")
@NotEmpty(message = "User ID can not be empty")
@DBRef
private String userId;


private String review;



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

@NotEmpty(message = "Start date can not be empty")
@NotNull(message = "Start date can not be null")
private Date startDate;

    @CreatedDate
    private Date CreatedAt;
    @NotEmpty(message = "End date can not be empty")
    @NotNull(message = "End date can not be null")
    private Date endDate;

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


    public @NotNull(message = "Listings ID can not me null") @NotEmpty(message = "Listings ID can not be empty") String getListingId() {
        return listingId;
    }

    public void setListingId(@NotNull(message = "Listings ID can not me null") @NotEmpty(message = "Listings ID can not be empty") String listingId) {
        this.listingId = listingId;
    }

    public @NotNull(message = "User ID can not be null") @NotEmpty(message = "User ID can not be empty") String getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(message = "User ID can not be null") @NotEmpty(message = "User ID can not be empty") String userId) {
        this.userId = userId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


    public @NotEmpty(message = "Start date can not be empty") @NotNull(message = "Start date can not be null") Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotEmpty(message = "Start date can not be empty") @NotNull(message = "Start date can not be null") Date startDate) {
        this.startDate = startDate;
    }

    public @NotEmpty(message = "End date can not be empty") @NotNull(message = "End date can not be null") Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotEmpty(message = "End date can not be empty") @NotNull(message = "End date can not be null") Date endDate) {
        this.endDate = endDate;
    }
}
