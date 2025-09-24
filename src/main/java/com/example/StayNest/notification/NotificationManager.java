package com.example.StayNest.notification;

import org.springframework.data.mongodb.core.messaging.Message;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

    private static NotificationManager instance;

    // ska det vara <Objekt> här eller ska den vara tom <> ?
    private List observers = new ArrayList<Object>();

    private NotificationManager() {}

    public static synchronized NotificationManager
    getInstance()
    {

        if (instance == null) {

            instance = new NotificationManager();
        }

        return instance;
    }

    public void
    registerObserver(NotificationObserver observer) {

        observers.add(observer);
    }

    public void sendNotification(Notification notification,
                                 Message message)
    {

        notification.send(message);

        notifyObservers(message.getContent());
    }

    private void notifyObservers(String content)
    {

        for (NotificationObserver o : observers) {

            o.update(content);
        }
    }
}
