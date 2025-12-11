/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.strategy;

/**
 *
 * @author arunagamage
 */
public class StudentFineStrategy implements FineStrategy {
    @Override public double calculateFine(int daysLate) { return daysLate * 50.0; }
    @Override public int borrowingLimit() { return 5; }
    @Override public int borrowingDays() { return 14; }
}

