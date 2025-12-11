/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.strategy;

import com.k2600143.slms.interfaces.K2600143_FineStrategy;

/**
 *
 * @author arunagamage
 */
// K2600143_FacultyFineStrategy.java
public class K2600143_FacultyFineStrategy implements K2600143_FineStrategy {
    @Override public double calculateFine(int daysLate) { return daysLate * 20.0; }
    @Override public int borrowingLimit() { return 10; }
    @Override public int borrowingDays() { return 30; }
}

