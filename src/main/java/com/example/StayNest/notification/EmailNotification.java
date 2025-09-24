package com.example.StayNest.notification;

import com.example.StayNest.decorator.EncryptedMessage;
import com.example.StayNest.decorator.TimestampedMessage;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.User;

import  com.example.StayNest.notification.Notification;
import com.example.StayNest.strategy.DelayedSendStrategy;
// behöver fömodlinger mer import

public class EmailNotification extends Notification {
    public void emailNotification(String message) {
        System.out.println("Created Email Notification");


        Message baseMessage = () -> message;

        Message encrypted
                = new EncryptedMessage(baseMessage);

        Message decorated
                = new TimestampedMessage(encrypted);

        NotificationManager notificationManager = new NotificationManager();

        // Create notification type

        Notification notification
                = NotificationFactory.createNotification(
                "email");

        notification.setStrategy(
                new DelayedSendStrategy());

        notificationManager.sendNotification(notification, decorated);
    }
}
