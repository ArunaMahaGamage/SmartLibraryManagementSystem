/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.model;

/**
 *
 * @author arunagamage
 */
// K2600143_Book.java
import com.k2600143.slms.state.K2600143_BookState;
import com.k2600143.slms.service.K2600143_LibraryService;
import com.k2600143.slms.state.K2600143_AvailableState;
import java.util.ArrayList;
import java.util.List;

public class K2600143_Book {
    private final String bookId;
    private final String title;
    private final String author;
    private final String category;
    private final String isbn;
    private final List<K2600143_BorrowTransaction> borrowedHistory = new ArrayList<>();
    private K2600143_BookState state;

    // Optional metadata via Builder
    private final List<String> tags;
    private final String edition;
    private final String review;

    public K2600143_Book(Builder builder) {
        this.bookId = builder.bookId;
        this.title = builder.title;
        this.author = builder.author;
        this.category = builder.category;
        this.isbn = builder.isbn;
        this.tags = builder.tags;
        this.edition = builder.edition;
        this.review = builder.review;
        this.state = new K2600143_AvailableState();
    }

    public static class Builder {
        private final String bookId;
        private final String title;
        private String author;
        private String category;
        private String isbn;
        private List<String> tags = new ArrayList<>();
        private String edition;
        private String review;

        public Builder(String bookId, String title) {
            this.bookId = bookId;
            this.title = title;
        }

        public Builder author(String a) { this.author = a; return this; }
        public Builder category(String c) { this.category = c; return this; }
        public Builder isbn(String i) { this.isbn = i; return this; }
        public Builder tags(List<String> t) { this.tags = t; return this; }
        public Builder edition(String e) { this.edition = e; return this; }
        public Builder review(String r) { this.review = r; return this; }

        public K2600143_Book build() { return new K2600143_Book(this); }
    }

    public void setState(K2600143_BookState state) { this.state = state; }
    public K2600143_BookState getState() { return state; }
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }
    public List<K2600143_BorrowTransaction> getBorrowedHistory() { return borrowedHistory; }

    public void borrow(K2600143_User user, K2600143_LibraryService service) { state.handleBorrow(this, user, service); }
    public void returnBook(K2600143_User user, K2600143_LibraryService service) { state.handleReturn(this, user, service); }
    public void reserve(K2600143_User user, K2600143_LibraryService service) { state.handleReserve(this, user, service); }

    public String availabilityStatus() { return state.getName(); }
}

