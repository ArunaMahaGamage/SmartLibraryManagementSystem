/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.state;

import com.k2600143.slms.interfaces.K2600143_BookState;
import com.k2600143.slms.model.K2600143_Book;
import com.k2600143.slms.model.K2600143_User;
import com.k2600143.slms.service.K2600143_LibraryService;

/**
 *
 * @author arunagamage
 */
// K2600143_ReservedState.java
public class K2600143_ReservedState implements K2600143_BookState {
    @Override
    public void handleBorrow(K2600143_Book book, K2600143_User user, K2600143_LibraryService service) {
        if (service.isUserFirstInReservationQueue(book, user)) {
            service.processBorrow(book, user);
            book.setState(new K2600143_BorrowedState());
            service.popReservation(book);
        } else {
            throw new IllegalStateException("Book is reserved. You are not first in the queue.");
        }
    }

    @Override
    public void handleReturn(K2600143_Book book, K2600143_User user, K2600143_LibraryService service) {
        throw new IllegalStateException("Book is reserved and not currently borrowed.");
    }

    @Override
    public void handleReserve(K2600143_Book book, K2600143_User user, K2600143_LibraryService service) {
        service.processReserve(book, user);
    }

    @Override
    public String getName() { return "Reserved"; }
}

