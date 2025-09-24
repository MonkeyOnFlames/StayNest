package com.example.StayNest.decorator;


import com.example.StayNest.notification.Message;

public class EncryptedMessage extends NotificationDecorator {

    public EncryptedMessage(Message message) {

        super(message);

    }
    public String getContent() {

        return " " + message.getContent();

    }

}
