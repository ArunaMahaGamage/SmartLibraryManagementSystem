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
public class AvailableState implements BookState {
    @Override
    public void handleBorrow(Book book, User user, LibraryService service) {
        service.processBorrow(book, user);
        book.setState(new BorrowedState());
    }

    @Override
    public void handleReturn(Book book, User user, LibraryService service) {
        throw new IllegalStateException("Book is already available. Cannot return.");
    }

    @Override
    public void handleReserve(Book book, User user, LibraryService service) {
        service.processReserve(book, user);
        book.setState(new ReservedState());
    }

    @Override
    public String getName() { return "Available"; }
}

