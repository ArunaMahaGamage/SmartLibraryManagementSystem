/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.k2600143.slms;

import com.k2600143.slms.command.K2600143_BorrowCommand;
import com.k2600143.slms.command.K2600143_ReserveCommand;
import com.k2600143.slms.command.K2600143_ReturnCommand;
import com.k2600143.slms.decorator.K2600143_FeaturedBook;
import com.k2600143.slms.decorator.K2600143_RecommendedBook;
import com.k2600143.slms.enums.K2600143_MembershipType;
import com.k2600143.slms.command.K2600143_Command;
import com.k2600143.slms.model.K2600143_Book;
import com.k2600143.slms.model.K2600143_User;
import com.k2600143.slms.service.K2600143_LibraryService;
import com.k2600143.slms.service.K2600143_ReportService;
import java.util.List;

/**
 *
 * @author arunagamage
 */
public class SmartLibraryManagementSystem {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        K2600143_LibraryService library = new K2600143_LibraryService();
        K2600143_ReportService report = new K2600143_ReportService(library);

        // Users
        K2600143_User student = new K2600143_User("U1", "Aruna", "aruna@example.com", "0771234567", K2600143_MembershipType.STUDENT);
        K2600143_User faculty = new K2600143_User("U2", "Dr. Silva", "silva@example.com", "0712345678", K2600143_MembershipType.FACULTY);
        K2600143_User guest = new K2600143_User("U3", "Guest User", "guest@example.com", "0757654321", K2600143_MembershipType.GUEST);

        library.addUser(student);
        library.addUser(faculty);
        library.addUser(guest);

        // Books via Builder
        K2600143_Book b1 = new K2600143_Book.Builder("B1", "Design Patterns")
                .author("Gamma et al.").category("Software").isbn("978-0201633610")
                .tags(List.of("Patterns", "OOP")).edition("1st").review("Classic text").build();
        K2600143_Book b2 = new K2600143_Book.Builder("B2", "Clean Code")
                .author("Robert C. Martin").category("Software").isbn("978-0132350884").build();

        // Decorator examples
        K2600143_Book featuredB1 = new K2600143_FeaturedBook(b1);
        K2600143_Book recommendedB2 = new K2600143_RecommendedBook(b2);

        library.addBook(b1);
        library.addBook(b2);

        System.out.println("Initial availability: " + b1.getTitle() + " = " + b1.availabilityStatus());

        // Commands
        K2600143_Command borrowB1ByStudent = new K2600143_BorrowCommand(b1, student, library);
        borrowB1ByStudent.execute();
        System.out.println("After borrow: " + b1.getTitle() + " = " + b1.availabilityStatus());

        // Reserve by guest while borrowed
        K2600143_Command reserveB1ByGuest = new K2600143_ReserveCommand(b1, guest, library);
        reserveB1ByGuest.execute();
        System.out.println("Reservation queue exists? " + library.hasActiveReservation(b1));
        System.out.println("Current state: " + b1.availabilityStatus());

        // Return by student
        K2600143_Command returnB1ByStudent = new K2600143_ReturnCommand(b1, student, library);
        returnB1ByStudent.execute();
        System.out.println("After return: " + b1.getTitle() + " = " + b1.availabilityStatus());

        // Borrow by guest (first in queue)
        K2600143_Command borrowB1ByGuest = new K2600143_BorrowCommand(b1, guest, library);
        borrowB1ByGuest.execute();
        System.out.println("After guest borrow: " + b1.getTitle() + " = " + b1.availabilityStatus());

        // Decorator description demo
        System.out.println("Decorated descriptions:");
        System.out.println(((K2600143_FeaturedBook) featuredB1).getDescription());
        System.out.println(((K2600143_RecommendedBook) recommendedB2).getDescription());

        // Reports
        report.printMostBorrowed(5);
        report.printOverdue();

        // Simple test harness
        runSimpleTests(library, student, faculty, guest, b2);
    }

    private static void runSimpleTests(K2600143_LibraryService library,
                                       K2600143_User student,
                                       K2600143_User faculty,
                                       K2600143_User guest,
                                       K2600143_Book b2) {
        System.out.println("\n=== Simple Test Cases ===");

        // Test 1: Borrowing within limit (student)
        try {
            new K2600143_BorrowCommand(b2, student, library).execute();
            assert "Borrowed".equals(b2.availabilityStatus()) : "Student borrow should set state Borrowed";
            System.out.println("Test 1 passed: Student borrowing within limit.");
        } catch (Exception e) {
            System.out.println("Test 1 failed: " + e.getMessage());
        }

        // Test 2: Reservation notification when returned
        try {
            new K2600143_ReserveCommand(b2, faculty, library).execute();
            new K2600143_ReturnCommand(b2, student, library).execute();
            assert "Reserved".equals(b2.availabilityStatus()) : "After return with reservation, state should be Reserved";
            System.out.println("Test 2 passed: Reservation notification and state Reserved.");
        } catch (Exception e) {
            System.out.println("Test 2 failed: " + e.getMessage());
        }

        // Test 3: Fine calculation path
        try {
            new K2600143_BorrowCommand(b2, faculty, library).execute();
            new K2600143_ReturnCommand(b2, faculty, library).execute();
            System.out.println("Test 3 executed: Fine calculation path exercised.");
        } catch (Exception e) {
            System.out.println("Test 3 failed: " + e.getMessage());
        }

        // Test 4: Borrowing limit for guest
        try {
            K2600143_Book b3 = new K2600143_Book.Builder("B3", "Refactoring").author("Martin Fowler").build();
            K2600143_Book b4 = new K2600143_Book.Builder("B4", "Effective Java").author("Joshua Bloch").build();
            library.addBook(b3); library.addBook(b4);
            new K2600143_BorrowCommand(b3, guest, library).execute();
            new K2600143_BorrowCommand(b4, guest, library).execute();
            K2600143_Book b5 = new K2600143_Book.Builder("B5", "The Pragmatic Programmer").author("Andrew Hunt").build();
            library.addBook(b5);
            try {
                new K2600143_BorrowCommand(b5, guest, library).execute();
                System.out.println("Test 4 failed: Guest exceeded limit without error.");
            } catch (IllegalStateException expected) {
                System.out.println("Test 4 passed: Guest borrowing limit enforced.");
            }
        } catch (Exception e) {
            System.out.println("Test 4 failed: " + e.getMessage());
        }

        // Test 5: Command undo
        try {
            K2600143_Book b6 = new K2600143_Book.Builder("B6", "Algorithms").author("Sedgewick").build();
            library.addBook(b6);
            K2600143_BorrowCommand cmd = new K2600143_BorrowCommand(b6, student, library);
            cmd.execute();
            assert "Borrowed".equals(b6.availabilityStatus());
            cmd.undo();
            assert "Available".equals(b6.availabilityStatus());
            System.out.println("Test 5 passed: Command undo works for borrow/return.");
        } catch (Exception e) {
            System.out.println("Test 5 failed: " + e.getMessage());
        }
    }
}
