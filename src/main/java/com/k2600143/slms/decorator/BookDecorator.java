/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.decorator;

import com.k2600143.slms.model.Book;

/**
 *
 * @author arunagamage
 */
public abstract class BookDecorator extends Book {
    protected final Book wrapped;

    public BookDecorator(Book wrapped) {
//        super(new Book.Builder(wrapped.getBookId(), wrapped.getTitle())
//                .author(wrapped.getAuthor()).category(wrapped.getCategory()).isbn(wrapped.getIsbn())
//                .build());

        super(new Book.Builder(wrapped.getBookId(), wrapped.getTitle()));
        this.wrapped = wrapped;
    }

    public String getDescription() {
        return wrapped.getTitle() + " by " + wrapped.getAuthor();
    }
}

