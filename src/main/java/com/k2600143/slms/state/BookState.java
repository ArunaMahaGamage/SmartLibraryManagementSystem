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
public interface BookState {
    void handleBorrow(Book book, User user, LibraryService service);
    void handleReturn(Book book, User user, LibraryService service);
    void handleReserve(Book book, User user, LibraryService service);
    String getName();
}
