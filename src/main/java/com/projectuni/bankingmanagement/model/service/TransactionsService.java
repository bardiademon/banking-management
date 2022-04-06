package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.model.repository.TransactionsRepository;
import org.springframework.stereotype.Service;

@Service
public record TransactionsService(TransactionsRepository transactionsRepository)
{
}
