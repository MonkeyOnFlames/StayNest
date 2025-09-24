package com.example.StayNest.decorator;

import org.springframework.data.mongodb.core.messaging.Message;

public class TimestampedMessage extends NotificationDecorator {

    public TimestampedMessage(Message message) {

        super(message);

    }


    public String getContent() {

        return message.getContent() + " @ " +
                java.time.LocalDateTime.now();

    }

}
