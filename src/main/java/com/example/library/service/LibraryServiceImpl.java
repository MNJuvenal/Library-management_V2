package com.example.library.service;

import com.example.library.exception.BookNotAvailableException;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.MemberNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public LibraryServiceImpl(BookRepository bookRepository,
                              MemberRepository memberRepository,
                              LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void addBook(Book book) {
        if (bookRepository.existsById(book.getId())) {
            throw new IllegalArgumentException("A book with this ID already exists.");
        }
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        bookRepository.save(book);
    }

    @Override
    public void removeBook(String bookId) {
        Book book = searchBookById(bookId);
        if (!book.isAvailable()) {
            throw new IllegalStateException("Cannot remove a borrowed book.");
        }
        bookRepository.delete(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book searchBookById(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found: " + bookId));
    }

    @Override
    public List<Book> searchBookByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public void addMember(Member member) {
        if (memberRepository.existsById(member.getId())) {
            throw new IllegalArgumentException("A member with this ID already exists.");
        }
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("A member with this email already exists.");
        }
        memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found: " + memberId));
    }

    @Override
    public void borrowBook(String bookId, String memberId) {
        Book book = searchBookById(bookId);
        Member member = getMemberById(memberId);

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is already borrowed.");
        }

        if (loanRepository.findByBook_IdAndReturnedFalse(bookId).isPresent()) {
            throw new BookNotAvailableException("An active loan already exists for this book.");
        }

        long activeLoans = loanRepository.countByMember_IdAndReturnedFalse(memberId);
        if (activeLoans >= member.getMaxBooksAllowed()) {
            throw new IllegalStateException("This member has reached the borrowing limit.");
        }

        Loan loan = new Loan("LN-" + System.currentTimeMillis(), book, member);
        book.markAsBorrowed();
        loanRepository.save(loan);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(String bookId, String memberId) {
        Book book = searchBookById(bookId);
        Member member = getMemberById(memberId);

        Loan loan = loanRepository.findByBook_IdAndReturnedFalse(bookId)
                .orElseThrow(() -> new IllegalStateException("No active loan found for this book."));

        if (!loan.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("This member did not borrow the selected book.");
        }

        loan.closeLoan();
        book.markAsAvailable();

        loanRepository.save(loan);
        bookRepository.save(book);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}
