/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.decorator;

import com.k2600143.slms.model.K2600143_Book;

/**
 *
 * @author arunagamage
 */
public abstract class K2600143_BookDecorator extends K2600143_Book {
    protected final K2600143_Book wrapped;

    public K2600143_BookDecorator(K2600143_Book wrapped) {
//        super(new K2600143_Book.Builder(wrapped.getBookId(), wrapped.getTitle())
//                .author(wrapped.getAuthor()).category(wrapped.getCategory()).isbn(wrapped.getIsbn())
//                .build());

        super(new K2600143_Book.Builder(wrapped.getBookId(), wrapped.getTitle()));
        this.wrapped = wrapped;
    }

    public String getDescription() {
        return wrapped.getTitle() + " by " + wrapped.getAuthor();
    }
}

