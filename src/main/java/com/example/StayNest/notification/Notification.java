package com.example.StayNest.notification;

import com.example.StayNest.models.Booking;
import com.example.StayNest.strategy.SendStrategy;
import org.springframework.data.mongodb.core.messaging.Message;

public abstract class Notification {

    protected SendStrategy strategy;

    public void setStrategy(SendStrategy strategy)
    {

        this.strategy = strategy;
    }

    public void send(Message message)
    {

        if (strategy != null) {

            strategy.send(message);
        }
        else {

            System.out.println("No strategy defined.");
        }
    }

    public SendStrategy getStrategy() {
        return strategy;
    }

    //ska det finnas set här eller räcker det med get?
    public SendStrategy setStrategy() {
        return strategy;
    }
}

/*
public abstract class Notification {
    // Data som notification ska baseras på
    private Booking booking;

    public Notification(Booking booking) {
        this.booking = booking;
    }

    public abstract void send();

    public Booking getBooking(){
        return booking;
    }


    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
 */