package com.projectuni.bankingmanagement.model.repository;

import com.projectuni.bankingmanagement.model.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>
{
    List<Transactions> findAllByFromIdOrToId(final long depositFromId , final long depositToId);
}