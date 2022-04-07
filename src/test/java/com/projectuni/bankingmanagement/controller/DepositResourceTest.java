package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.DepositIsClosedException;
import com.projectuni.bankingmanagement.exception.InvalidAccountInventory;
import com.projectuni.bankingmanagement.exception.InvalidCreditExpirationDate;
import com.projectuni.bankingmanagement.exception.InvalidIncreaseDepositException;
import com.projectuni.bankingmanagement.exception.InvalidWithdrawalDepositException;
import com.projectuni.bankingmanagement.exception.InventoryIsNotEnoughException;
import com.projectuni.bankingmanagement.exception.MoneyTransferException;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToCustomerDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDepositDto;
import com.projectuni.bankingmanagement.model.dto.OpeningDepositDto;
import com.projectuni.bankingmanagement.model.enums.DepositCurrency;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.enums.DepositType;
import com.projectuni.bankingmanagement.model.service.DepositService;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.InternalServerErrorException;
import java.util.Arrays;
import java.util.List;

public class DepositResourceTest
{

    private DepositService depositService;

    @Before
    public void setUp() throws Exception
    {
        SpringConfig.config();

        depositService = SpringConfig.newInstance(DepositService.class);
    }

    @Test
    public void getDeposits()
    {
        try
        {
            ToDepositDto.to(depositService.getDeposits(Long.parseLong("5")));
            System.out.println("ok");
        }
        catch (NotFoundCustomerException | NotFoundDepositException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDeposits()
    {
        try
        {
            ToDepositDto.to(depositService.getDeposits());
            System.out.println("ok");
        }
        catch (NotFoundDepositException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getCustomersDeposits()
    {
        try
        {
            ToCustomerDto.to(depositService.getCustomerDeposits(Integer.parseInt("54")));
            System.out.println("ok");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getStatusDeposits()
    {
        try
        {
            depositService.changeStatus(5 , DepositStatus.BLOCKED_DEPOSIT);
            System.out.println("changed");
        }
        catch (NotFoundDepositException | DepositIsClosedException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void increaseDeposit()
    {
        try
        {
            long transactionId = depositService.increase(5 , 5484.56);
            System.out.println("increase: " + transactionId); // issue tracking
        }
        catch (NotFoundDepositException | InvalidAccountInventory | InvalidIncreaseDepositException | NullPointerException | InternalServerErrorException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void withdrawalDeposit()
    {
        try
        {
            long transactionId = depositService.withdrawal(6 , 5884.5);
            System.out.println("withdrawal: " + transactionId); // issue tracking
        }
        catch (NotFoundDepositException | InvalidAccountInventory | InvalidWithdrawalDepositException | InventoryIsNotEnoughException | NullPointerException | InternalServerErrorException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void openingADeposit()
    {
        final OpeningDepositDto openingDepositDto = new OpeningDepositDto();
        openingDepositDto.setDepositStatus(DepositStatus.OPEN);
        openingDepositDto.setDepositType(DepositType.LONG_TIME);
        openingDepositDto.setDepositCurrency(DepositCurrency.DOLLAR);
        openingDepositDto.setAccountInventory(545125.1254);
        openingDepositDto.setCustomerIds(List.of(4 , 8 , 5 , 78));
        openingDepositDto.setCreditExpirationDate(5);

        try
        {
            depositService.openingDeposit(openingDepositDto);
            System.out.println("opened");
        }
        catch (NullPointerException | NotFoundCustomerException | InvalidAccountInventory | InvalidCreditExpirationDate e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void moneyTransfer()
    {
        try
        {
            final long[] issueTracking = depositService.moneyTransfer(5 , 2 , 5466.65);

            System.out.println("done!");
            System.out.println(Arrays.toString(issueTracking));
        }
        catch (MoneyTransferException | NullPointerException | InvalidAccountInventory | NotFoundDepositException | InventoryIsNotEnoughException | InvalidWithdrawalDepositException | InvalidIncreaseDepositException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getDepositBalance()
    {
        try
        {
            depositService.getDepositBalance(5);
            System.out.println("done!");
        }
        catch (NotFoundDepositException e)
        {
            e.printStackTrace();
        }
    }
}