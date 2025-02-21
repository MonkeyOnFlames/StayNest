package com.example.StayNest.dto;

import com.example.StayNest.models.Enviroment;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.ListingType;
import com.example.StayNest.models.Restrictions;

import java.util.List;
import java.util.Set;

public class ListingResponseGetById {
    private String id;
    private String userId;
    private String firstName;
    private String name;
    private String location;
    private String description;
    private Double price;
    private Set<ListingType> listingTypes;
    private String listingPolicy;
    private List<Enviroment> environment;
    private List<Restrictions> restrictions;
    private List<String> pictureURLs;
    private List<Listing.Availability> availabilities;
    private String createdAt;

    public ListingResponseGetById() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getListingPolicy() {
        return listingPolicy;
    }

    public void setListingPolicy(String listingPolicy) {
        this.listingPolicy = listingPolicy;
    }

    public List<Enviroment> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<Enviroment> environment) {
        this.environment = environment;
    }

    public List<Restrictions> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restrictions> restrictions) {
        this.restrictions = restrictions;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
