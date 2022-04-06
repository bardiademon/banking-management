package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DTOTransaction;
import com.projectuni.bankingmanagement.model.entity.Transactions;

public final class ToTransaction
{
    private ToTransaction()
    {
    }

    public static Transactions toTransaction(final DTOTransaction dtoTransaction)
    {
        final Transactions transaction = new Transactions();
        transaction.setTransactionsType(dtoTransaction.getTransactionsType());
        transaction.setTransactionsStatus(dtoTransaction.getTransactionsStatus());
        transaction.setDescription(dtoTransaction.getDescription());
        transaction.setFrom(dtoTransaction.getFrom());
        transaction.setTo(dtoTransaction.getTo());
        transaction.setPrice(dtoTransaction.getPrice());
        return transaction;
    }
}
