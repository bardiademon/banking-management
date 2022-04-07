package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.LoanDto;
import com.projectuni.bankingmanagement.model.entity.Loan;

public final class ToLoan
{
    private ToLoan()
    {
    }

    public static Loan to(final LoanDto loanDto)
    {
        final Loan loan = new Loan();
        loan.setLoanType(loanDto.getLoanType());
        loan.setInterestRate(loanDto.getInterestRate().interestRate());
        loan.setThePrincipalAmountOfTheLoan(loanDto.getThePrincipalAmountOfTheLoan());
        loan.setTotalNumberOfInstallments(loanDto.getTotalNumberOfInstallments());
        return loan;
    }

}
