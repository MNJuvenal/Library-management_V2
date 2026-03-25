package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @NotBlank
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @Column(unique = true)
    @NotBlank
    private String isbn;

    private boolean available = true;

    public Book() {
    }

    public Book(String id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void markAsBorrowed() {
        this.available = false;
    }

    public void markAsAvailable() {
        this.available = true;
    }

    public String getDetails() {
        return "[" + id + "] " + title + " by " + author + " - " + (available ? "Available" : "Borrowed");
    }
}
