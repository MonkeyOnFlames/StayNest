package com.example.StayNest.decorator;


import org.springframework.data.mongodb.core.messaging.Message;

public class EncryptedMessage extends NotificationDecorator {

    public EncryptedMessage(Message message) {

        super(Message);

    }
    public String getContent() {

        return " " + message.getContent();

    }

}
