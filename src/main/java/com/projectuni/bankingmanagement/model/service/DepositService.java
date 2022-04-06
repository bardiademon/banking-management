package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.model.entity.Customers;
import com.projectuni.bankingmanagement.model.entity.Deposit;
import com.projectuni.bankingmanagement.model.repository.CustomersRepository;
import com.projectuni.bankingmanagement.model.repository.DepositRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record DepositService(DepositRepository depositRepository , CustomersRepository customersRepository)
{

    public List<Deposit> getDeposits(final long customerId) throws NotFoundCustomerException, NotFoundDepositException
    {
        final Optional<Customers> customerById = customersRepository.findById(customerId);
        if (customerById.isPresent())
        {
            final List<Deposit> depositByCustomerId = depositRepository.findByCustomerId(customerId);
            if (depositByCustomerId.size() > 0) return depositByCustomerId;
            else throw new NotFoundDepositException();
        }
        else throw new NotFoundCustomerException();
    }
}
