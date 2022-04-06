package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.model.repository.DepositRepository;
import org.springframework.stereotype.Service;

@Service
public record DepositService(DepositRepository depositRepository)
{
}
