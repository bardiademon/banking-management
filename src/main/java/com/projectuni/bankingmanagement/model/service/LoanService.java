package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.model.repository.LoanRepository;
import org.springframework.stereotype.Repository;

@Repository
public record LoanService(LoanRepository loanRepository)
{
}
