package com.example.StayNest.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = "listings")
public class Listing {
    @Id
    private String id;

    @DBRef
    private User user;

    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotEmpty(message = "Location cannot be empty")
    @NotNull(message = "Location cannot be null")
    private String location;

    @NotEmpty(message = "Description cannot be empty")
    @NotNull(message = "Description cannot be null")
    private String description;

    @NotEmpty(message = "Price cannot be empty")
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotEmpty(message = "Type cannot be empty")
    @NotNull(message = "Type cannot be null")
    private Set<ListingType> listingTypes;

    @NotEmpty(message = "Listing policy cannot be empty")
    @NotNull(message = "Listing policy cannot be null")
    private String listingPolicy;

    private List<Enviroment> environment;

    private List<Restrictions> restrictions;

    @NotEmpty(message = "Pictures cannot be empty")
    @NotNull(message = "Pictures cannot be null")
    private List<String> pictureURLs;

    @NotEmpty(message = "Availability cannot be empty")
    @NotNull(message = "Availability cannot be null")
    private List<Availability> availabilities;

    @CreatedDate
    private Date createdAt;


    public Listing() {
        // pictureURL max 6 photos
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @NotEmpty(message = "Name cannot be empty") @NotNull(message = "Name cannot be null") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name cannot be empty") @NotNull(message = "Name cannot be null") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "Location cannot be empty") @NotNull(message = "Location cannot be null") String getLocation() {
        return location;
    }

    public void setLocation(@NotEmpty(message = "Location cannot be empty") @NotNull(message = "Location cannot be null") String location) {
        this.location = location;
    }

    public @NotEmpty(message = "Description cannot be empty") @NotNull(message = "Description cannot be null") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Description cannot be empty") @NotNull(message = "Description cannot be null") String description) {
        this.description = description;
    }

    @NotEmpty(message = "Price cannot be empty")
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    public Double getPrice() {
        return price;
    }

    public void setPrice(@NotEmpty(message = "Price cannot be empty") @NotNull(message = "Price cannot be null") @Positive(message = "Price must be greater than 0") Double price) {
        this.price = price;
    }

    public @NotEmpty(message = "Type cannot be empty") @NotNull(message = "Type cannot be null") Set<ListingType> getListingTypes() {
        return listingTypes;
    }

    public void setListingTypes(@NotEmpty(message = "Type cannot be empty") @NotNull(message = "Type cannot be null") Set<ListingType> listingTypes) {
        this.listingTypes = listingTypes;
    }

    public @NotEmpty(message = "Listing policy cannot be empty") @NotNull(message = "Listing policy cannot be null") String getListingPolicy() {
        return listingPolicy;
    }

    public void setListingPolicy(@NotEmpty(message = "Listing policy cannot be empty") @NotNull(message = "Listing policy cannot be null") String listingPolicy) {
        this.listingPolicy = listingPolicy;
    }

    public List<Enviroment> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<Enviroment> environments) {
        this.environment = environments;
    }

    public List<Restrictions> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restrictions> restrictions) {
        this.restrictions = restrictions;
    }

    public @NotEmpty(message = "Pictures cannot be empty") @NotNull(message = "Pictures cannot be null") List<String> getPictureURLs() {
        return pictureURLs;
    }

    public void setPictureURLs(@NotEmpty(message = "Pictures cannot be empty") @NotNull(message = "Pictures cannot be null") List<String> pictureURLs) {
        this.pictureURLs = pictureURLs;
    }

    public @NotEmpty(message = "Availability cannot be empty") @NotNull(message = "Availability cannot be null") List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(@NotEmpty(message = "Availability cannot be empty") @NotNull(message = "Availability cannot be null") List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public static class Availability {
        private Date startDate;
        private Date endDate;

        public Availability() {
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }
}


