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

public class Reservation {
    private final String reservationId;
    private final User user;
    private final Book book;
    private final LocalDate reservationDate;

    public Reservation(String reservationId, User user, Book book, LocalDate reservationDate) {
        this.reservationId = reservationId;
        this.user = user;
        this.book = book;
        this.reservationDate = reservationDate;
    }

    public String getReservationId() { return reservationId; }
    public User getUser() { return user; }
    public Book getBook() { return book; }
    public LocalDate getReservationDate() { return reservationDate; }
}

