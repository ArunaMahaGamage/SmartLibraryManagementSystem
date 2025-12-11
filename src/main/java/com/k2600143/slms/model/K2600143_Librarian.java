/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.model;

import com.k2600143.slms.enums.K2600143_MembershipType;

/**
 *
 * @author arunagamage
 */
public class K2600143_Librarian extends K2600143_User {
    public K2600143_Librarian(String userId, String name, String email, String contactNumber) {
        super(userId, name, email, contactNumber, K2600143_MembershipType.FACULTY);
    }
}

