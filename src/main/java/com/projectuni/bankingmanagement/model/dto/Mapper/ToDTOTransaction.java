package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DTOTransaction;
import com.projectuni.bankingmanagement.model.entity.Transactions;

import java.util.ArrayList;
import java.util.List;

public final class ToDTOTransaction
{
    private ToDTOTransaction()
    {
    }

    public static List<DTOTransaction> to(final List<Transactions> transactions)
    {
        final List<DTOTransaction> dtoTransactions = new ArrayList<>();

        if (transactions != null && transactions.size() > 0)
        {
            for (final Transactions transaction : transactions) dtoTransactions.add(to(transaction));
        }

        return dtoTransactions;
    }

    public static DTOTransaction to(final Transactions transaction)
    {
        final DTOTransaction dtoTransaction = new DTOTransaction();
        dtoTransaction.setTransactionsType(transaction.getTransactionsType());
        dtoTransaction.setTransactionsStatus(transaction.getTransactionsStatus());
        dtoTransaction.setDescription(transaction.getDescription());
        dtoTransaction.setFromDeposit(ToDTODeposit.to(transaction.getFrom()));
        dtoTransaction.setToDeposit(ToDTODeposit.to(transaction.getTo()));
        dtoTransaction.setPrice(transaction.getPrice());
        return dtoTransaction;
    }
}
