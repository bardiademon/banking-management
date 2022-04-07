package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.TransactionDto;
import com.projectuni.bankingmanagement.model.entity.Transactions;

public final class ToTransaction
{
    private ToTransaction()
    {
    }

    public static Transactions toTransaction(final TransactionDto transactionDto)
    {
        final Transactions transaction = new Transactions();
        transaction.setTransactionsType(transactionDto.getTransactionsType());
        transaction.setTransactionsStatus(transactionDto.getTransactionsStatus());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setFrom(transactionDto.getFrom());
        transaction.setTo(transactionDto.getTo());
        transaction.setPrice(transactionDto.getPrice());
        return transaction;
    }
}
