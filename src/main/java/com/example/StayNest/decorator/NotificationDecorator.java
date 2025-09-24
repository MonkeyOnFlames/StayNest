package com.example.StayNest.decorator;

import org.springframework.data.mongodb.core.messaging.Message;

public abstract class NotificationDecorator implements Message {

    protected Message message;

    public NotificationDecorator(Message message) {

        this.message = message;

    }

}
