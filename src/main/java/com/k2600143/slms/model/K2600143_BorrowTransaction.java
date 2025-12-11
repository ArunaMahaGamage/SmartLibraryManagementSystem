/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.model;

/**
 *
 * @author arunagamage
 */
import java.time.LocalDate;

public class K2600143_BorrowTransaction {
    private final String transactionId;
    private final K2600143_User user;
    private final K2600143_Book book;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;

    public K2600143_BorrowTransaction(String transactionId,
                                      K2600143_User user,
                                      K2600143_Book book,
                                      LocalDate borrowDate,
                                      LocalDate dueDate) {
        this.transactionId = transactionId;
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public String getTransactionId() { return transactionId; }
    public K2600143_User getUser() { return user; }
    public K2600143_Book getBook() { return book; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public double getFine() { return fine; }
    public void setFine(double fine) { this.fine = fine; }
}

