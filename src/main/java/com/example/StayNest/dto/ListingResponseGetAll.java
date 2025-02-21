package com.example.StayNest.dto;

import com.example.StayNest.models.Enviroment;
import com.example.StayNest.models.Listing;

import java.util.List;

public class ListingResponseGetAll {
    private String id;
    private String firstName;
    private String name;
    private String location;
    private Double price;
    private List<Enviroment> environment;
    private List<String> pictureURLs;
    private List<Listing.Availability> availabilities;

    public ListingResponseGetAll() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Enviroment> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<Enviroment> environment) {
        this.environment = environment;
    }

    public List<String> getPictureURLs() {
        return pictureURLs;
    }

    public void setPictureURLs(List<String> pictureURLs) {
        this.pictureURLs = pictureURLs;
    }

    public List<Listing.Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Listing.Availability> availabilities) {
        this.availabilities = availabilities;
    }
}
