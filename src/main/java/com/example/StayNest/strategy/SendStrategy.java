package com.example.StayNest.strategy;

import com.example.StayNest.notification.Message;
//ska den vara en abstract här?
public abstract interface SendStrategy {

    void send(Message message);
}
