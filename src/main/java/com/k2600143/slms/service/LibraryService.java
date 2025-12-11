/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.service;

/**
 *
 * @author arunagamage
 */
import com.k2600143.slms.enums.NotificationType;
import com.k2600143.slms.model.Book;
import com.k2600143.slms.model.BorrowTransaction;
import com.k2600143.slms.model.Reservation;
import com.k2600143.slms.model.User;
import com.k2600143.slms.observer.Notification;
import com.k2600143.slms.observer.NotificationManager;
import java.time.LocalDate;
import java.util.*;

public class LibraryService {
    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Deque<Reservation>> reservations = new HashMap<>();
    private final List<BorrowTransaction> transactions = new ArrayList<>();
    private final NotificationManager notificationManager = new NotificationManager();

    // Book management
    public void addBook(Book book) { books.put(book.getBookId(), book); }
    public void updateBook(Book book) { books.put(book.getBookId(), book); }
    public void removeBook(String bookId) { books.remove(bookId); reservations.remove(bookId); }

    // User management
    public void addUser(User user) { users.put(user.getUserId(), user); notificationManager.attach(user); }
    public User getUser(String userId) { return users.get(userId); }
    public Book getBook(String bookId) { return books.get(bookId); }

    // Reservation helpers
    public boolean hasActiveReservation(Book book) {
        return reservations.containsKey(book.getBookId()) && !reservations.get(book.getBookId()).isEmpty();
    }

    public boolean isUserFirstInReservationQueue(Book book, User user) {
        Deque<Reservation> q = reservations.get(book.getBookId());
        return q != null && !q.isEmpty() && q.peekFirst().getUser().equals(user);
    }

    public void popReservation(Book book) {
        Deque<Reservation> q = reservations.get(book.getBookId());
        if (q != null && !q.isEmpty()) q.pollFirst();
    }

    // Core operations called by states
    public void processBorrow(Book book, User user) {
        long active = user.getBorrowedHistory().stream().filter(t -> t.getReturnDate() == null).count();
        if (active >= user.getFineStrategy().borrowingLimit()) {
            throw new IllegalStateException("Borrowing limit exceeded for " + user.getName());
        }
        LocalDate borrow = LocalDate.now();
        LocalDate due = borrow.plusDays(user.getFineStrategy().borrowingDays());
        BorrowTransaction tx = new BorrowTransaction(UUID.randomUUID().toString(), user, book, borrow, due);
        transactions.add(tx);
        user.getBorrowedHistory().add(tx);
        book.getBorrowedHistory().add(tx);

        notificationManager.notifyAllObservers(new Notification(UUID.randomUUID().toString(), NotificationType.DUE_DATE,
                "Book '" + book.getTitle() + "' due on " + due + " for " + user.getName())
        );
    }

    public void processReturn(Book book, User user) {
        Optional<BorrowTransaction> opt = user.getBorrowedHistory().stream()
                .filter(t -> t.getBook().equals(book) && t.getReturnDate() == null).findFirst();
        if (opt.isEmpty()) {
            throw new IllegalStateException("No active borrow transaction found for return.");
        }
        BorrowTransaction tx = opt.get();
        LocalDate today = LocalDate.now();
        tx.setReturnDate(today);

        int daysLate = 0;
        if (today.isAfter(tx.getDueDate())) {
            daysLate = (int) (today.toEpochDay() - tx.getDueDate().toEpochDay());
        }
        double fine = user.getFineStrategy().calculateFine(daysLate);
        tx.setFine(fine);

        if (daysLate > 0) {
            notificationManager.notifyAllObservers(new Notification(UUID.randomUUID().toString(), NotificationType.OVERDUE,
                    user.getName() + " returned '" + book.getTitle() + "' late by " + daysLate + " days. Fine: LKR " + fine)
            );
        }
    }

    public void processReserve(Book book, User user) {
        reservations.computeIfAbsent(book.getBookId(), k -> new ArrayDeque<>());
        reservations.get(book.getBookId()).addLast(new Reservation(UUID.randomUUID().toString(), user, book, LocalDate.now())
        );
        notificationManager.notifyAllObservers(new Notification(UUID.randomUUID().toString(), NotificationType.RESERVATION_AVAILABLE,
                user.getName() + " reserved '" + book.getTitle() + "'. You will be notified when available.")
        );
    }

    public void notifyReservationAvailable(Book book) {
        Deque<Reservation> q = reservations.get(book.getBookId());
        if (q != null && !q.isEmpty()) {
            User nextUser = q.peekFirst().getUser();
            nextUser.onNotify(new Notification(UUID.randomUUID().toString(),
                    NotificationType.RESERVATION_AVAILABLE,
                    "Reserved book '" + book.getTitle() + "' is now available for you."));
        }
    }

    public void cancelReservation(Book book, User user) {
        Deque<Reservation> q = reservations.get(book.getBookId());
        if (q == null || q.isEmpty()) return;
        q.removeIf(r -> r.getUser().equals(user));
    }

    // Reports
    public List<Book> mostBorrowedBooks(int topN) {
        return books.values().stream()
                .sorted((a, b) -> Integer.compare(b.getBorrowedHistory().size(), a.getBorrowedHistory().size()))
                .limit(topN).toList();
    }

    public List<User> activeBorrowers(int topN) {
        return users.values().stream()
                .sorted((a, b) -> Long.compare(
                        b.getBorrowedHistory().stream().filter(t -> t.getReturnDate() == null).count(),
                        a.getBorrowedHistory().stream().filter(t -> t.getReturnDate() == null).count()
                ))
                .limit(topN).toList();
    }

    public List<BorrowTransaction> overdueTransactions() {
        LocalDate today = LocalDate.now();
        return transactions.stream()
                .filter(t -> t.getReturnDate() == null && t.getDueDate().isBefore(today))
                .toList();
    }
}

