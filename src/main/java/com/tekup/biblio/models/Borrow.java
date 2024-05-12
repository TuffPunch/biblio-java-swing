package com.tekup.biblio.models;

import java.sql.Date;

public class Borrow {

    private int borrowId;
    private Student student;
    private Book book;
    private Date borrowDate;
    private Date returnDate;

    // Getters and Setters (omitted for brevity)

    public Borrow(int borrowId, Student student, Book book, Date borrowDate, java.sql.Date returnDate) {
        this.borrowId = borrowId;
        this.student = student;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}

