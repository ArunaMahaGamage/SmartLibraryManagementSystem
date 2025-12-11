/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.model;

/**
 *
 * @author arunagamage
 */
// K2600143_User.java
import com.k2600143.slms.enums.K2600143_MembershipType;
import com.k2600143.slms.interfaces.K2600143_FineStrategy;
import com.k2600143.slms.interfaces.K2600143_NotificationObserver;
import com.k2600143.slms.observer.K2600143_Notification;
import com.k2600143.slms.strategy.K2600143_FacultyFineStrategy;
import com.k2600143.slms.strategy.K2600143_GuestFineStrategy;
import com.k2600143.slms.strategy.K2600143_StudentFineStrategy;
import java.util.ArrayList;
import java.util.List;

public class K2600143_User implements K2600143_NotificationObserver {
    private final String userId;
    private final String name;
    private final String email;
    private final String contactNumber;
    private final K2600143_MembershipType membershipType;
    private final List<K2600143_BorrowTransaction> borrowedHistory = new ArrayList<>();
    private K2600143_FineStrategy fineStrategy;

    public K2600143_User(String userId, String name, String email, String contactNumber, K2600143_MembershipType membershipType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.membershipType = membershipType;
        this.fineStrategy = strategyFor(membershipType);
    }

    private K2600143_FineStrategy strategyFor(K2600143_MembershipType type) {
        return switch (type) {
            case STUDENT -> new K2600143_StudentFineStrategy();
            case FACULTY -> new K2600143_FacultyFineStrategy();
            case GUEST -> new K2600143_GuestFineStrategy();
        };
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public K2600143_MembershipType getMembershipType() { return membershipType; }
    public K2600143_FineStrategy getFineStrategy() { return fineStrategy; }
    public List<K2600143_BorrowTransaction> getBorrowedHistory() { return borrowedHistory; }

    @Override
    public void onNotify(K2600143_Notification notification) {
        System.out.println("Notify " + name + " [" + email + "]: " + notification.getMessage());
    }
}

