package com.example.StayNest.notification;

import com.example.StayNest.notification.Message;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

    private static NotificationManager instance;


    private List<NotificationObserver> observers = new ArrayList<>();

    public NotificationManager() {}

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
