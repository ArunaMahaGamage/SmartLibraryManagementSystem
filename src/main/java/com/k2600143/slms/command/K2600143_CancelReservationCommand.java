/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.command;

import com.k2600143.slms.model.K2600143_Book;
import com.k2600143.slms.model.K2600143_User;
import com.k2600143.slms.service.K2600143_LibraryService;

/**
 *
 * @author arunagamage
 */
public class K2600143_CancelReservationCommand implements K2600143_Command {
    private final K2600143_Book book;
    private final K2600143_User user;
    private final K2600143_LibraryService service;

    public K2600143_CancelReservationCommand(K2600143_Book book, K2600143_User user, K2600143_LibraryService service) {
        this.book = book; this.user = user; this.service = service;
    }

    @Override public void execute() { service.cancelReservation(book, user); }
    @Override public void undo() { book.reserve(user, service); }
}

