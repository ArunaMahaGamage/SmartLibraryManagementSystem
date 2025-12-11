/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.k2600143.slms;

import com.k2600143.slms.command.BorrowCommand;
import com.k2600143.slms.command.ReserveCommand;
import com.k2600143.slms.command.ReturnCommand;
import com.k2600143.slms.decorator.FeaturedBook;
import com.k2600143.slms.decorator.RecommendedBook;
import com.k2600143.slms.enums.MembershipType;
import com.k2600143.slms.model.Book;
import com.k2600143.slms.model.User;
import com.k2600143.slms.service.LibraryService;
import com.k2600143.slms.service.ReportService;
import java.util.List;
import com.k2600143.slms.command.Command;

/**
 *
 * @author arunagamage
 */
public class SmartLibraryManagementSystem {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        LibraryService library = new LibraryService();
        ReportService report = new ReportService(library);

        // Users
        User student = new User("U1", "Aruna", "aruna@example.com", "0771234567", MembershipType.STUDENT);
        User faculty = new User("U2", "Dr. Silva", "silva@example.com", "0712345678", MembershipType.FACULTY);
        User guest = new User("U3", "Guest User", "guest@example.com", "0757654321", MembershipType.GUEST);

        library.addUser(student);
        library.addUser(faculty);
        library.addUser(guest);

        // Books via Builder
        Book b1 = new Book.Builder("B1", "Design Patterns")
                .author("Gamma et al.").category("Software").isbn("978-0201633610")
                .tags(List.of("Patterns", "OOP")).edition("1st").review("Classic text").build();
        Book b2 = new Book.Builder("B2", "Clean Code")
                .author("Robert C. Martin").category("Software").isbn("978-0132350884").build();

        // Decorator examples
        Book featuredB1 = new FeaturedBook(b1);
        Book recommendedB2 = new RecommendedBook(b2);

        library.addBook(b1);
        library.addBook(b2);

        System.out.println("Initial availability: " + b1.getTitle() + " = " + b1.availabilityStatus());

        // Commands
        Command borrowB1ByStudent = new BorrowCommand(b1, student, library);
        borrowB1ByStudent.execute();
        System.out.println("After borrow: " + b1.getTitle() + " = " + b1.availabilityStatus());

        // Reserve by guest while borrowed
        Command reserveB1ByGuest = new ReserveCommand(b1, guest, library);
        reserveB1ByGuest.execute();
        System.out.println("Reservation queue exists? " + library.hasActiveReservation(b1));
        System.out.println("Current state: " + b1.availabilityStatus());

        // Return by student
        Command returnB1ByStudent = new ReturnCommand(b1, student, library);
        returnB1ByStudent.execute();
        System.out.println("After return: " + b1.getTitle() + " = " + b1.availabilityStatus());

        // Borrow by guest (first in queue)
        Command borrowB1ByGuest = new BorrowCommand(b1, guest, library);
        borrowB1ByGuest.execute();
        System.out.println("After guest borrow: " + b1.getTitle() + " = " + b1.availabilityStatus());

        // Decorator description demo
        System.out.println("Decorated descriptions:");
        System.out.println(((FeaturedBook) featuredB1).getDescription());
        System.out.println(((RecommendedBook) recommendedB2).getDescription());

        // Reports
        report.printMostBorrowed(5);
        report.printOverdue();

        // Simple test harness
        runSimpleTests(library, student, faculty, guest, b2);
    }

    private static void runSimpleTests(LibraryService library,
                                       User student,
                                       User faculty,
                                       User guest,
                                       Book b2) {
        System.out.println("\n=== Simple Test Cases ===");

        // Test 1: Borrowing within limit (student)
        try {
            new BorrowCommand(b2, student, library).execute();
            assert "Borrowed".equals(b2.availabilityStatus()) : "Student borrow should set state Borrowed";
            System.out.println("Test 1 passed: Student borrowing within limit.");
        } catch (Exception e) {
            System.out.println("Test 1 failed: " + e.getMessage());
        }

        // Test 2: Reservation notification when returned
        try {
            new ReserveCommand(b2, faculty, library).execute();
            new ReturnCommand(b2, student, library).execute();
            assert "Reserved".equals(b2.availabilityStatus()) : "After return with reservation, state should be Reserved";
            System.out.println("Test 2 passed: Reservation notification and state Reserved.");
        } catch (Exception e) {
            System.out.println("Test 2 failed: " + e.getMessage());
        }

        // Test 3: Fine calculation path
        try {
            new BorrowCommand(b2, faculty, library).execute();
            new ReturnCommand(b2, faculty, library).execute();
            System.out.println("Test 3 executed: Fine calculation path exercised.");
        } catch (Exception e) {
            System.out.println("Test 3 failed: " + e.getMessage());
        }

        // Test 4: Borrowing limit for guest
        try {
            Book b3 = new Book.Builder("B3", "Refactoring").author("Martin Fowler").build();
            Book b4 = new Book.Builder("B4", "Effective Java").author("Joshua Bloch").build();
            library.addBook(b3); library.addBook(b4);
            new BorrowCommand(b3, guest, library).execute();
            new BorrowCommand(b4, guest, library).execute();
            Book b5 = new Book.Builder("B5", "The Pragmatic Programmer").author("Andrew Hunt").build();
            library.addBook(b5);
            try {
                new BorrowCommand(b5, guest, library).execute();
                System.out.println("Test 4 failed: Guest exceeded limit without error.");
            } catch (IllegalStateException expected) {
                System.out.println("Test 4 passed: Guest borrowing limit enforced.");
            }
        } catch (Exception e) {
            System.out.println("Test 4 failed: " + e.getMessage());
        }

        // Test 5: Command undo
        try {
            Book b6 = new Book.Builder("B6", "Algorithms").author("Sedgewick").build();
            library.addBook(b6);
            BorrowCommand cmd = new BorrowCommand(b6, student, library);
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
