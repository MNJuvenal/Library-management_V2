package com.example.library.repository;

import com.example.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, String> {
    Optional<Loan> findByBook_IdAndReturnedFalse(String bookId);
    long countByMember_IdAndReturnedFalse(String memberId);
    List<Loan> findByMember_Id(String memberId);
}
