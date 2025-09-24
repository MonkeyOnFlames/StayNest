package com.example.StayNest.strategy;

import org.springframework.data.mongodb.core.messaging.Message;
//ska den vara en abstract här?
public abstract interface SendStrategy {

    void send(Message message);
}
