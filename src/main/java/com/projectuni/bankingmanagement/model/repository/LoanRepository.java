package com.projectuni.bankingmanagement.model.repository;

import com.projectuni.bankingmanagement.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>
{
    List<Loan> findAllByDepositId(final long deposit);
}