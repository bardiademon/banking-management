package com.projectuni.bankingmanagement.model.repository;

import com.projectuni.bankingmanagement.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    Customer findByNationalCode(final int nationalCode);
}
