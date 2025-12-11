/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.observer;

import com.k2600143.slms.enums.K2600143_NotificationType;

/**
 *
 * @author arunagamage
 */
public class K2600143_Notification {
    private final String id;
    private final K2600143_NotificationType type;
    private final String message;

    public K2600143_Notification(String id, K2600143_NotificationType type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public String getId() { return id; }
    public K2600143_NotificationType getType() { return type; }
    public String getMessage() { return message; }
}

