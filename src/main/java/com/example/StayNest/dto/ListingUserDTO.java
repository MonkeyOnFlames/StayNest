package com.example.StayNest.dto;

public class ListingUserDTO {
    private String userId;
    private String firstName;

    public ListingUserDTO() {
    }

    public ListingUserDTO(String userId, String firstName) {
        this.userId = userId;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
