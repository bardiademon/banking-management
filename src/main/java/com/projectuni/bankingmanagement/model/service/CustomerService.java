package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository)
{
}
