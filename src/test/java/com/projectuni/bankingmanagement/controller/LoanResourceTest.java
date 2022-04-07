package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.InvalidAccountInventory;
import com.projectuni.bankingmanagement.exception.InvalidWithdrawalDepositException;
import com.projectuni.bankingmanagement.exception.InventoryIsNotEnoughException;
import com.projectuni.bankingmanagement.exception.LoanIsClosedException;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.exception.NotFoundLoanException;
import com.projectuni.bankingmanagement.model.dto.LoanAllocationDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToLoanDto;
import com.projectuni.bankingmanagement.model.enums.InterestRate;
import com.projectuni.bankingmanagement.model.enums.LoanType;
import com.projectuni.bankingmanagement.model.service.LoanService;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.InternalServerErrorException;

public class LoanResourceTest
{
    private LoanService loanService;

    @Before
    public void setUp() throws Exception
    {
        SpringConfig.config();

        loanService = SpringConfig.newInstance(LoanService.class);
    }

    @Test
    public void loanAllocation()
    {
        final LoanAllocationDto loanAllocationDto = new LoanAllocationDto();
        loanAllocationDto.setLoanType(LoanType.MARRIAGE_LOAN);
        loanAllocationDto.setDepositId(2);
        loanAllocationDto.setInterestRate(InterestRate.PERCENT_4);
        loanAllocationDto.setThePrincipalAmountOfTheLoan(1000);
        loanAllocationDto.setTotalNumberOfInstallments(10);

        try
        {
            loanService.loanAllocation(loanAllocationDto);
            System.out.println("done!");
        }
        catch (NotFoundDepositException | NullPointerException | InternalServerErrorException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void loanPayments()
    {
        try
        {
            loanService.loanPayments(5);
            System.out.println("done!");
        }
        catch (NotFoundLoanException | LoanIsClosedException | InventoryIsNotEnoughException | InvalidAccountInventory | NotFoundDepositException | InvalidWithdrawalDepositException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getLoans()
    {
        try
        {
            ToLoanDto.to(loanService.getLoans());
            System.out.println("done!");
        }
        catch (NotFoundLoanException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLoans()
    {
        try
        {
            ToLoanDto.to(loanService.getLoans(6));
            System.out.println("done!");
        }
        catch (NotFoundLoanException | NotFoundDepositException e)
        {
            e.printStackTrace();
        }
    }
}