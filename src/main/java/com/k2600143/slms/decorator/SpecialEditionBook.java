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
// SpecialEditionBook.java
public class SpecialEditionBook extends BookDecorator {
    public SpecialEditionBook(Book wrapped) { super(wrapped); }
    @Override
    public String getDescription() { return super.getDescription() + " [Special Edition]"; }
}

