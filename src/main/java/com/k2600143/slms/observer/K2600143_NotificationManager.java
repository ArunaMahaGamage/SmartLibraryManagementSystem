/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.observer;

/**
 *
 * @author arunagamage
 */
import com.k2600143.slms.interfaces.K2600143_NotificationObserver;
import java.util.ArrayList;
import java.util.List;

public class K2600143_NotificationManager {
    private final List<K2600143_NotificationObserver> observers = new ArrayList<>();

    public void attach(K2600143_NotificationObserver observer) {
        observers.add(observer);
    }

    public void detach(K2600143_NotificationObserver observer) {
        observers.remove(observer);
    }

    public void notifyAllObservers(K2600143_Notification notification) {
        for (K2600143_NotificationObserver o : observers) {
            o.onNotify(notification);
        }
    }
}

