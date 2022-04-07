package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.exception.NotFoundTransactionsException;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToTransactionDto;
import com.projectuni.bankingmanagement.model.service.DepositService;
import com.projectuni.bankingmanagement.model.service.TransactionsService;
import org.junit.Before;
import org.junit.Test;

public class TransactionsResourceTest
{
    private TransactionsService transactionsService;
    private DepositService depositService;

    @Before
    public void setUp() throws Exception
    {
        SpringConfig.config();

        transactionsService = SpringConfig.newInstance(TransactionsService.class);
        depositService = SpringConfig.newInstance(DepositService.class);
    }

    @Test
    public void getDepositTransactions()
    {
        try
        {
            ToTransactionDto.to(transactionsService.depositTransactions(4 , depositService));
            System.out.println("done!");
        }
        catch (NotFoundTransactionsException | NotFoundDepositException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getByNumber()
    {
        try
        {
            ToTransactionDto.to(transactionsService.getById(1));
            System.out.println("done!");
        }
        catch (NotFoundTransactionsException e)
        {
            e.printStackTrace();
        }
    }
}