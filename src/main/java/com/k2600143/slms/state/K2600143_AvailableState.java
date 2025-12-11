/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.state;

import com.k2600143.slms.model.K2600143_Book;
import com.k2600143.slms.model.K2600143_User;
import com.k2600143.slms.service.K2600143_LibraryService;

/**
 *
 * @author arunagamage
 */
public class K2600143_AvailableState implements K2600143_BookState {
    @Override
    public void handleBorrow(K2600143_Book book, K2600143_User user, K2600143_LibraryService service) {
        service.processBorrow(book, user);
        book.setState(new K2600143_BorrowedState());
    }

    @Override
    public void handleReturn(K2600143_Book book, K2600143_User user, K2600143_LibraryService service) {
        throw new IllegalStateException("Book is already available. Cannot return.");
    }

    @Override
    public void handleReserve(K2600143_Book book, K2600143_User user, K2600143_LibraryService service) {
        service.processReserve(book, user);
        book.setState(new K2600143_ReservedState());
    }

    @Override
    public String getName() { return "Available"; }
}

