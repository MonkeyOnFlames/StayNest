package com.example.StayNest.dto;

import com.example.StayNest.models.Listing;

import java.time.LocalDate;

public class BookingRequestDTO {

    private Listing listing;


    private Double totalAmount;


    private LocalDate startDate;


    private LocalDate endDate;

    public BookingRequestDTO() {
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }


    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
