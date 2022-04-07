package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.exception.NotFoundTransactionsException;
import com.projectuni.bankingmanagement.model.dto.TransactionDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToTransaction;
import com.projectuni.bankingmanagement.model.entity.Transactions;
import com.projectuni.bankingmanagement.model.repository.TransactionsRepository;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import java.util.List;
import java.util.Optional;

@Service
public record TransactionsService(TransactionsRepository transactionsRepository)
{
    public long newTransaction(final TransactionDto transactionDto) throws NullPointerException, InternalServerErrorException
    {
        if (transactionDto != null)
        {
            Transactions transaction = ToTransaction.toTransaction(transactionDto);
            transaction = transactionsRepository.save(transaction);
            if (transaction.getId() <= 0) throw new InternalServerErrorException();
            else return transaction.getId();
        }
        else throw new NullPointerException("Transaction is null");
    }

    public List<Transactions> depositTransactions(final long depositId , final DepositService depositService) throws NotFoundTransactionsException, NotFoundDepositException
    {
        depositService.getDepositById(depositId);

        final List<Transactions> transactions = transactionsRepository.findAllByFromIdOrToId(depositId , depositId);
        if (transactions.size() > 0) return transactions;
        else throw new NotFoundTransactionsException();
    }

    public Transactions getById(final long transactionId) throws NotFoundTransactionsException
    {
        final Optional<Transactions> TransactionById = transactionsRepository.findById(transactionId);
        if (TransactionById.isPresent()) return TransactionById.get();
        else throw new NotFoundTransactionsException();
    }
}
