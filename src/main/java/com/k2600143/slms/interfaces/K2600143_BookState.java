/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.interfaces;

import com.k2600143.slms.model.K2600143_Book;
import com.k2600143.slms.model.K2600143_User;
import com.k2600143.slms.service.K2600143_LibraryService;

/**
 *
 * @author arunagamage
 */
public interface K2600143_BookState {
    void handleBorrow(K2600143_Book book, K2600143_User user, K2600143_LibraryService service);
    void handleReturn(K2600143_Book book, K2600143_User user, K2600143_LibraryService service);
    void handleReserve(K2600143_Book book, K2600143_User user, K2600143_LibraryService service);
    String getName();
}
