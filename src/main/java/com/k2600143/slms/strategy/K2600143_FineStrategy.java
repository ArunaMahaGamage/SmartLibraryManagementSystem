/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.k2600143.slms.strategy;

/**
 *
 * @author arunagamage
 */
public interface K2600143_FineStrategy {
    double calculateFine(int daysLate);
    int borrowingLimit();
    int borrowingDays();
}
