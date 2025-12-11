/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.state;

import com.k2600143.slms.model.Book;
import com.k2600143.slms.model.User;
import com.k2600143.slms.service.LibraryService;

/**
 *
 * @author arunagamage
 */
// ReservedState.java
public class ReservedState implements BookState {
    @Override
    public void handleBorrow(Book book, User user, LibraryService service) {
        if (service.isUserFirstInReservationQueue(book, user)) {
            service.processBorrow(book, user);
            book.setState(new BorrowedState());
            service.popReservation(book);
        } else {
            throw new IllegalStateException("Book is reserved. You are not first in the queue.");
        }
    }

    @Override
    public void handleReturn(Book book, User user, LibraryService service) {
        throw new IllegalStateException("Book is reserved and not currently borrowed.");
    }

    @Override
    public void handleReserve(Book book, User user, LibraryService service) {
        service.processReserve(book, user);
    }

    @Override
    public String getName() { return "Reserved"; }
}

