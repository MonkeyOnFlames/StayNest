package com.example.StayNest.decorator;


import com.example.StayNest.notification.Message;

public class TimestampedMessage extends NotificationDecorator {

    public TimestampedMessage(Message message) {

        super(message);

    }

@Super
    public String getContent() {

        return message.getContent() + " @ " +
                java.time.LocalDateTime.now();

    }

}
