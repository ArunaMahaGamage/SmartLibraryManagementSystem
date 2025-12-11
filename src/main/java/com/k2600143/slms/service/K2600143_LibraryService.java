/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.k2600143.slms.service;

/**
 *
 * @author arunagamage
 */
import com.k2600143.slms.enums.K2600143_NotificationType;
import com.k2600143.slms.model.K2600143_Book;
import com.k2600143.slms.model.K2600143_BorrowTransaction;
import com.k2600143.slms.model.K2600143_Reservation;
import com.k2600143.slms.model.K2600143_User;
import com.k2600143.slms.observer.K2600143_Notification;
import com.k2600143.slms.observer.K2600143_NotificationManager;
import java.time.LocalDate;
import java.util.*;

public class K2600143_LibraryService {
    private final Map<String, K2600143_Book> books = new HashMap<>();
    private final Map<String, K2600143_User> users = new HashMap<>();
    private final Map<String, Deque<K2600143_Reservation>> reservations = new HashMap<>();
    private final List<K2600143_BorrowTransaction> transactions = new ArrayList<>();
    private final K2600143_NotificationManager notificationManager = new K2600143_NotificationManager();

    // Book management
    public void addBook(K2600143_Book book) { books.put(book.getBookId(), book); }
    public void updateBook(K2600143_Book book) { books.put(book.getBookId(), book); }
    public void removeBook(String bookId) { books.remove(bookId); reservations.remove(bookId); }

    // User management
    public void addUser(K2600143_User user) { users.put(user.getUserId(), user); notificationManager.attach(user); }
    public K2600143_User getUser(String userId) { return users.get(userId); }
    public K2600143_Book getBook(String bookId) { return books.get(bookId); }

    // Reservation helpers
    public boolean hasActiveReservation(K2600143_Book book) {
        return reservations.containsKey(book.getBookId()) && !reservations.get(book.getBookId()).isEmpty();
    }

    public boolean isUserFirstInReservationQueue(K2600143_Book book, K2600143_User user) {
        Deque<K2600143_Reservation> q = reservations.get(book.getBookId());
        return q != null && !q.isEmpty() && q.peekFirst().getUser().equals(user);
    }

    public void popReservation(K2600143_Book book) {
        Deque<K2600143_Reservation> q = reservations.get(book.getBookId());
        if (q != null && !q.isEmpty()) q.pollFirst();
    }

    // Core operations called by states
    public void processBorrow(K2600143_Book book, K2600143_User user) {
        long active = user.getBorrowedHistory().stream().filter(t -> t.getReturnDate() == null).count();
        if (active >= user.getFineStrategy().borrowingLimit()) {
            throw new IllegalStateException("Borrowing limit exceeded for " + user.getName());
        }
        LocalDate borrow = LocalDate.now();
        LocalDate due = borrow.plusDays(user.getFineStrategy().borrowingDays());
        K2600143_BorrowTransaction tx = new K2600143_BorrowTransaction(UUID.randomUUID().toString(), user, book, borrow, due);
        transactions.add(tx);
        user.getBorrowedHistory().add(tx);
        book.getBorrowedHistory().add(tx);

        notificationManager.notifyAllObservers(
            new K2600143_Notification(UUID.randomUUID().toString(), K2600143_NotificationType.DUE_DATE,
                "Book '" + book.getTitle() + "' due on " + due + " for " + user.getName())
        );
    }

    public void processReturn(K2600143_Book book, K2600143_User user) {
        Optional<K2600143_BorrowTransaction> opt = user.getBorrowedHistory().stream()
                .filter(t -> t.getBook().equals(book) && t.getReturnDate() == null).findFirst();
        if (opt.isEmpty()) {
            throw new IllegalStateException("No active borrow transaction found for return.");
        }
        K2600143_BorrowTransaction tx = opt.get();
        LocalDate today = LocalDate.now();
        tx.setReturnDate(today);

        int daysLate = 0;
        if (today.isAfter(tx.getDueDate())) {
            daysLate = (int) (today.toEpochDay() - tx.getDueDate().toEpochDay());
        }
        double fine = user.getFineStrategy().calculateFine(daysLate);
        tx.setFine(fine);

        if (daysLate > 0) {
            notificationManager.notifyAllObservers(
                new K2600143_Notification(UUID.randomUUID().toString(), K2600143_NotificationType.OVERDUE,
                    user.getName() + " returned '" + book.getTitle() + "' late by " + daysLate + " days. Fine: LKR " + fine)
            );
        }
    }

    public void processReserve(K2600143_Book book, K2600143_User user) {
        reservations.computeIfAbsent(book.getBookId(), k -> new ArrayDeque<>());
        reservations.get(book.getBookId()).addLast(
            new K2600143_Reservation(UUID.randomUUID().toString(), user, book, LocalDate.now())
        );
        notificationManager.notifyAllObservers(
            new K2600143_Notification(UUID.randomUUID().toString(), K2600143_NotificationType.RESERVATION_AVAILABLE,
                user.getName() + " reserved '" + book.getTitle() + "'. You will be notified when available.")
        );
    }

    public void notifyReservationAvailable(K2600143_Book book) {
        Deque<K2600143_Reservation> q = reservations.get(book.getBookId());
        if (q != null && !q.isEmpty()) {
            K2600143_User nextUser = q.peekFirst().getUser();
            nextUser.onNotify(new K2600143_Notification(UUID.randomUUID().toString(),
                    K2600143_NotificationType.RESERVATION_AVAILABLE,
                    "Reserved book '" + book.getTitle() + "' is now available for you."));
        }
    }

    public void cancelReservation(K2600143_Book book, K2600143_User user) {
        Deque<K2600143_Reservation> q = reservations.get(book.getBookId());
        if (q == null || q.isEmpty()) return;
        q.removeIf(r -> r.getUser().equals(user));
    }

    // Reports
    public List<K2600143_Book> mostBorrowedBooks(int topN) {
        return books.values().stream()
                .sorted((a, b) -> Integer.compare(b.getBorrowedHistory().size(), a.getBorrowedHistory().size()))
                .limit(topN).toList();
    }

    public List<K2600143_User> activeBorrowers(int topN) {
        return users.values().stream()
                .sorted((a, b) -> Long.compare(
                        b.getBorrowedHistory().stream().filter(t -> t.getReturnDate() == null).count(),
                        a.getBorrowedHistory().stream().filter(t -> t.getReturnDate() == null).count()
                ))
                .limit(topN).toList();
    }

    public List<K2600143_BorrowTransaction> overdueTransactions() {
        LocalDate today = LocalDate.now();
        return transactions.stream()
                .filter(t -> t.getReturnDate() == null && t.getDueDate().isBefore(today))
                .toList();
    }
}

