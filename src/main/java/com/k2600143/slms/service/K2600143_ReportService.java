/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.service;

/**
 *
 * @author arunagamage
 */
import com.k2600143.slms.model.K2600143_Book;
import com.k2600143.slms.model.K2600143_BorrowTransaction;
import java.util.List;

public class K2600143_ReportService {
    private final K2600143_LibraryService service;

    public K2600143_ReportService(K2600143_LibraryService service) {
        this.service = service;
    }

    public void printMostBorrowed(int topN) {
        List<K2600143_Book> top = service.mostBorrowedBooks(topN);
        System.out.println("Most Borrowed Books:");
        for (K2600143_Book b : top) {
            System.out.println("- " + b.getTitle() + " (" + b.getBorrowedHistory().size() + " borrows)");
        }
    }

    public void printOverdue() {
        var list = service.overdueTransactions();
        System.out.println("Overdue Transactions:");
        for (K2600143_BorrowTransaction t : list) {
            System.out.println("- " + t.getUser().getName() + " | " + t.getBook().getTitle() + " due on " + t.getDueDate());
        }
    }
}

