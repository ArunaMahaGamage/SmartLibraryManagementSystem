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
// RecommendedBook.java
public class RecommendedBook extends BookDecorator {
    public RecommendedBook(Book wrapped) { super(wrapped); }
    @Override
    public String getDescription() { return super.getDescription() + " [Recommended]"; }
}

