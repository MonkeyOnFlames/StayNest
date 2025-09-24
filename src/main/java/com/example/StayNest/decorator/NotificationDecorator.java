package com.example.StayNest.decorator;


import com.example.StayNest.notification.Message;

public abstract class NotificationDecorator implements Message {

    protected Message message;

    public NotificationDecorator(Message message) {

        this.message = message;

    }

}
