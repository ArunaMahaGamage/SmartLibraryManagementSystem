/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.model;

/**
 *
 * @author arunagamage
 */
// User.java
import com.k2600143.slms.enums.MembershipType;
import com.k2600143.slms.observer.Notification;
import com.k2600143.slms.strategy.FacultyFineStrategy;
import com.k2600143.slms.strategy.GuestFineStrategy;
import com.k2600143.slms.strategy.StudentFineStrategy;
import java.util.ArrayList;
import java.util.List;
import com.k2600143.slms.observer.NotificationObserver;
import com.k2600143.slms.strategy.FineStrategy;

public class User implements NotificationObserver {
    private final String userId;
    private final String name;
    private final String email;
    private final String contactNumber;
    private final MembershipType membershipType;
    private final List<BorrowTransaction> borrowedHistory = new ArrayList<>();
    private FineStrategy fineStrategy;

    public User(String userId, String name, String email, String contactNumber, MembershipType membershipType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.membershipType = membershipType;
        this.fineStrategy = strategyFor(membershipType);
    }

    private FineStrategy strategyFor(MembershipType type) {
        return switch (type) {
            case STUDENT -> new StudentFineStrategy();
            case FACULTY -> new FacultyFineStrategy();
            case GUEST -> new GuestFineStrategy();
        };
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public MembershipType getMembershipType() { return membershipType; }
    public FineStrategy getFineStrategy() { return fineStrategy; }
    public List<BorrowTransaction> getBorrowedHistory() { return borrowedHistory; }

    @Override
    public void onNotify(Notification notification) {
        System.out.println("Notify " + name + " [" + email + "]: " + notification.getMessage());
    }
}

