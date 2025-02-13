package com.example.StayNest.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    @Min(value = 0, message = "Price has to be positive")
    private double price;

    @NotEmpty(message = "Type cannot be empty")
    @NotNull(message = "Type cannot be null")
    private ListingType type;

    @NotEmpty(message = "Environments cannot be empty")
    @NotNull(message = "Environments cannot be null")
    private Environment environment;

    @NotEmpty(message = "Restrictions cannot be empty")
    @NotNull(message = "Restrictions cannot be null")
    private String restrictions;

    @NotEmpty(message = "Pictures cannot be empty")
    @NotNull(message = "Pictures cannot be null")
    @Size(min = 1, max = 6, message = "You can only have 1-6 pictures")
    private List<String> pictureURLs;

    @NotEmpty(message = "Availability cannot be empty")
    @NotNull(message = "Availability cannot be null")
    private List<String> availability;


    public Listing() {
    }

    private class Environment {
        private int kWh;
        private boolean charge_post;
        private boolean recycle;
        private boolean isBike;
        private boolean solarPower;

        public Environment() {
        }

        public int getkWh() {
            return kWh;
        }

        public void setkWh(int kWh) {
            this.kWh = kWh;
        }

        public boolean isCharge_post() {
            return charge_post;
        }

        public void setCharge_post(boolean charge_post) {
            this.charge_post = charge_post;
        }

        public boolean isRecycle() {
            return recycle;
        }

        public void setRecycle(boolean recycle) {
            this.recycle = recycle;
        }

        public boolean isBike() {
            return isBike;
        }

        public void setBike(boolean bike) {
            isBike = bike;
        }

        public boolean isSolarPower() {
            return solarPower;
        }

        public void setSolarPower(boolean solarPower) {
            this.solarPower = solarPower;
        }
    }
}


