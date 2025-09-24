package com.example.StayNest.notification;

public class NotificationFactory {

    public static Notification
    createNotification(String type) {

        switch (type.toLowerCase()) {

            case "email":
                return new EmailNotification();

            default:
                throw new IllegalArgumentException(
                        "Unknown type: " + type);
        }
    }
}