/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.model;

import com.k2600143.slms.enums.MembershipType;

/**
 *
 * @author arunagamage
 */
public class Librarian extends User {
    public Librarian(String userId, String name, String email, String contactNumber) {
        super(userId, name, email, contactNumber, MembershipType.FACULTY);
    }
}

