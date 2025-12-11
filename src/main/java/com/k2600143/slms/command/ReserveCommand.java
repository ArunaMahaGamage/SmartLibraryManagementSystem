/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.command;

import com.k2600143.slms.model.Book;
import com.k2600143.slms.model.User;
import com.k2600143.slms.service.LibraryService;

/**
 *
 * @author arunagamage
 */
public class ReserveCommand implements Command {
    private final Book book;
    private final User user;
    private final LibraryService service;

    public ReserveCommand(Book book, User user, LibraryService service) {
        this.book = book; this.user = user; this.service = service;
    }

    @Override public void execute() { book.reserve(user, service); }
    @Override public void undo() { service.cancelReservation(book, user); }
}

