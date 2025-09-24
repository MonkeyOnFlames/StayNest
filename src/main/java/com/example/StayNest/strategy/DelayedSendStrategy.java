package com.example.StayNest.strategy;


import com.example.StayNest.notification.Message;


public class DelayedSendStrategy implements SendStrategy {

    public void send(Message message)
    {

        System.out.println("Sending message: "
                + message.getContent());
    }
}
