package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.LoanDto;
import com.projectuni.bankingmanagement.model.entity.Loan;

import java.util.ArrayList;
import java.util.List;

public final class ToLoanDto
{
    private ToLoanDto()
    {
    }

    public static List<LoanDto> to(final List<Loan> loans)
    {
        final List<LoanDto> loanDto = new ArrayList<>();

        if (loans != null && loans.size() > 0)
        {
            for (final Loan loan : loans) loanDto.add(to(loan));
        }

        return loanDto;
    }

    public static LoanDto to(final Loan loan)
    {
        final LoanDto loanDto = new LoanDto();
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setLoanStatus(loan.getLoanStatus());
        loanDto.setInterestRate(ToInterestRate.to(loan.getInterestRate()));
        loanDto.setDeposit(ToDepositDto.to(loan.getDeposit()));
        loanDto.setAmountPerInstallment(loan.getAmountPerInstallment());
        loanDto.setNumberOfRemainingInstallments(loan.getNumberOfRemainingInstallments());
        loanDto.setThePrincipalAmountOfTheLoan(loan.getThePrincipalAmountOfTheLoan());
        loanDto.setTotalNumberOfInstallments(loan.getTotalNumberOfInstallments());
        return loanDto;
    }
}
