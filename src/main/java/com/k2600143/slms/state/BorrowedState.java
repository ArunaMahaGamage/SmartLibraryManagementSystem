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
public class BorrowedState implements BookState {
    @Override
    public void handleBorrow(Book book, User user, LibraryService service) {
        throw new IllegalStateException("Book is already borrowed.");
    }

    @Override
    public void handleReturn(Book book, User user, LibraryService service) {
        service.processReturn(book, user);
        if (service.hasActiveReservation(book)) {
            book.setState(new ReservedState());
            service.notifyReservationAvailable(book);
        } else {
            book.setState(new AvailableState());
        }
    }

    @Override
    public void handleReserve(Book book, User user, LibraryService service) {
        service.processReserve(book, user);
    }

    @Override
    public String getName() { return "Borrowed"; }
}

