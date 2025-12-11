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
// K2600143_RecommendedBook.java
public class K2600143_RecommendedBook extends K2600143_BookDecorator {
    public K2600143_RecommendedBook(K2600143_Book wrapped) { super(wrapped); }
    @Override
    public String getDescription() { return super.getDescription() + " [Recommended]"; }
}

