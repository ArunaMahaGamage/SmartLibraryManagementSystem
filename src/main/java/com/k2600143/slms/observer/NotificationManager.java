/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.observer;

/**
 *
 * @author arunagamage
 */
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private final List<NotificationObserver> observers = new ArrayList<>();

    public void attach(NotificationObserver observer) {
        observers.add(observer);
    }

    public void detach(NotificationObserver observer) {
        observers.remove(observer);
    }

    public void notifyAllObservers(Notification notification) {
        for (NotificationObserver o : observers) {
            o.onNotify(notification);
        }
    }
}

