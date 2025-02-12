package com.example.StayNest.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "listings")
public class Listing {
    @Id
    private String id;

    @DBRef
    private User user;

    @Indexed(unique = true)
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotEmpty(message = "Price cannot be empty")
    @Min(value = 0, message = "Price has to be positive")
    private double price;

    @NotEmpty(message = "Type cannot be empty")
    private ListingType type;

    @NotEmpty(message = "Enviroments cannot be empty")
    private String enviroments;

    @NotEmpty(message = "Restrictions cannot be empty")
    private String restrictions;

    @NotEmpty(message = "You need at least one picture")
    private String pictureURL;

    @NotEmpty(message = "Availability cannot be empty")
    private List<String> availability;


    public Listing() {
    }
}
