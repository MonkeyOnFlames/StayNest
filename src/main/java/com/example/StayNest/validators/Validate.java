package com.example.StayNest.validators;

public class Validate {
    private boolean booking;
    private boolean date;
    private boolean available;
    private boolean conflict;
    private boolean auth;
    private boolean valid = true;

    public Validate(boolean booking, boolean date, boolean available, boolean conflict, boolean auth) {
        this.booking = booking;
        this.date = date;
        this.available = available;
        this.conflict = conflict;
        this.auth = auth;
    }

    public void setBooking(boolean booking) {
        this.booking = booking;
    }

    public boolean isBooking() {
        return booking;
    }

    public void setDate(boolean date) {
        this.date = date;
    }

    public boolean isDate() {
        return date;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public boolean isAvailable() {
        return available;
    }

    public boolean isConflict() {
        return conflict;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }


}
