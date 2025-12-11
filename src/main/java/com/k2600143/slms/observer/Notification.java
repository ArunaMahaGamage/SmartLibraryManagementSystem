/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.observer;

import com.k2600143.slms.enums.NotificationType;

/**
 *
 * @author arunagamage
 */
public class Notification {
    private final String id;
    private final NotificationType type;
    private final String message;

    public Notification(String id, NotificationType type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public String getId() { return id; }
    public NotificationType getType() { return type; }
    public String getMessage() { return message; }
}

