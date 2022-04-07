package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.TransactionDto;
import com.projectuni.bankingmanagement.model.entity.Transactions;

import java.util.ArrayList;
import java.util.List;

public final class ToTransactionDto
{
    private ToTransactionDto()
    {
    }

    public static List<TransactionDto> to(final List<Transactions> transactions)
    {
        final List<TransactionDto> transactionDtos = new ArrayList<>();

        if (transactions != null && transactions.size() > 0)
        {
            for (final Transactions transaction : transactions) transactionDtos.add(to(transaction));
        }

        return transactionDtos;
    }

    public static TransactionDto to(final Transactions transaction)
    {
        final TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionsType(transaction.getTransactionsType());
        transactionDto.setTransactionsStatus(transaction.getTransactionsStatus());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setFromDeposit(ToDepositDto.to(transaction.getFrom()));
        transactionDto.setToDeposit(ToDepositDto.to(transaction.getTo()));
        transactionDto.setPrice(transaction.getPrice());
        return transactionDto;
    }
}
