package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.Member;

import java.util.List;

public interface LibraryService {
    void addBook(Book book);
    void removeBook(String bookId);
    List<Book> getAllBooks();
    Book searchBookById(String bookId);
    List<Book> searchBookByTitle(String title);

    void addMember(Member member);
    List<Member> getAllMembers();
    Member getMemberById(String memberId);

    void borrowBook(String bookId, String memberId);
    void returnBook(String bookId, String memberId);
    List<Loan> getAllLoans();
}
