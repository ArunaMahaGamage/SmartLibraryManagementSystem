/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.model;

/**
 *
 * @author arunagamage
 */
import java.time.LocalDate;

public class K2600143_Reservation {
    private final String reservationId;
    private final K2600143_User user;
    private final K2600143_Book book;
    private final LocalDate reservationDate;

    public K2600143_Reservation(String reservationId, K2600143_User user, K2600143_Book book, LocalDate reservationDate) {
        this.reservationId = reservationId;
        this.user = user;
        this.book = book;
        this.reservationDate = reservationDate;
    }

    public String getReservationId() { return reservationId; }
    public K2600143_User getUser() { return user; }
    public K2600143_Book getBook() { return book; }
    public LocalDate getReservationDate() { return reservationDate; }
}

