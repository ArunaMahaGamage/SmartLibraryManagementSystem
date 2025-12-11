/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.service;

/**
 *
 * @author arunagamage
 */
import com.k2600143.slms.model.Book;
import com.k2600143.slms.model.BorrowTransaction;
import java.util.List;

public class ReportService {
    private final LibraryService service;

    public ReportService(LibraryService service) {
        this.service = service;
    }

    public void printMostBorrowed(int topN) {
        List<Book> top = service.mostBorrowedBooks(topN);
        System.out.println("Most Borrowed Books:");
        for (Book b : top) {
            System.out.println("- " + b.getTitle() + " (" + b.getBorrowedHistory().size() + " borrows)");
        }
    }

    public void printOverdue() {
        var list = service.overdueTransactions();
        System.out.println("Overdue Transactions:");
        for (BorrowTransaction t : list) {
            System.out.println("- " + t.getUser().getName() + " | " + t.getBook().getTitle() + " due on " + t.getDueDate());
        }
    }
}

