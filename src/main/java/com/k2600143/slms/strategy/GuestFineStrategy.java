/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.strategy;

/**
 *
 * @author arunagamage
 */
// K2600143_GuestFineStrategy.java
public class GuestFineStrategy implements FineStrategy {
    @Override public double calculateFine(int daysLate) { return daysLate * 100.0; }
    @Override public int borrowingLimit() { return 2; }
    @Override public int borrowingDays() { return 7; }
}

