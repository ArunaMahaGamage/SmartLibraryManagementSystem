/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.model;

/**
 *
 * @author arunagamage
 */
// Book.java
import com.k2600143.slms.service.LibraryService;
import com.k2600143.slms.state.AvailableState;
import java.util.ArrayList;
import java.util.List;
import com.k2600143.slms.state.BookState;

public class Book {
    private final String bookId;
    private final String title;
    private final String author;
    private final String category;
    private final String isbn;
    private final List<BorrowTransaction> borrowedHistory = new ArrayList<>();
    private BookState state;

    // Optional metadata via Builder
    private final List<String> tags;
    private final String edition;
    private final String review;

    public Book(Builder builder) {
        this.bookId = builder.bookId;
        this.title = builder.title;
        this.author = builder.author;
        this.category = builder.category;
        this.isbn = builder.isbn;
        this.tags = builder.tags;
        this.edition = builder.edition;
        this.review = builder.review;
        this.state = new AvailableState();
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

        public Book build() { return new Book(this); }
    }

    public void setState(BookState state) { this.state = state; }
    public BookState getState() { return state; }
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }
    public List<BorrowTransaction> getBorrowedHistory() { return borrowedHistory; }

    public void borrow(User user, LibraryService service) { state.handleBorrow(this, user, service); }
    public void returnBook(User user, LibraryService service) { state.handleReturn(this, user, service); }
    public void reserve(User user, LibraryService service) { state.handleReserve(this, user, service); }

    public String availabilityStatus() { return state.getName(); }
}

