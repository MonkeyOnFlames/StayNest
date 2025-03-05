package com.example.StayNest.dto;

import com.example.StayNest.models.Environment;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.ListingType;

import java.util.List;
import java.util.Set;

public class ListingResponseGetAll {
    private String id;
    private String firstName;
    private String name;
    private String location;
    private Double price;
    private Set<ListingType> listingTypes;
    private List<Environment> environment;
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

    public Set<ListingType> getListingTypes() {
        return listingTypes;
    }

    public void setListingTypes(Set<ListingType> listingTypes) {
        this.listingTypes = listingTypes;
    }

    public List<Environment> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<Environment> environment) {
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
