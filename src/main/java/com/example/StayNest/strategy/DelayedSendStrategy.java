package com.example.StayNest.strategy;

import org.springframework.data.mongodb.core.messaging.Message;

public class DelayedSendStrategy implements SendStrategy {

    public void send(Message message)
    {

        System.out.println("Sending message: "
                + message.getContent());
    }
}
