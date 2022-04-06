package com.projectuni.bankingmanagement.model.repository;

import com.projectuni.bankingmanagement.model.entity.Deposit;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long>
{
    List<Deposit> findByCustomers_Id(final long id);

    List<Deposit> findByCustomers_IdAndDepositStatus(final long id , final DepositStatus status);
}