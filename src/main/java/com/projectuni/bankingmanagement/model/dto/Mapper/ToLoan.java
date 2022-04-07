package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.LoanAllocationDto;
import com.projectuni.bankingmanagement.model.entity.Loan;

public final class ToLoan
{
    private ToLoan()
    {
    }

    public static Loan to(final LoanAllocationDto loanAllocationDto)
    {
        final Loan loan = new Loan();
        loan.setLoanType(loanAllocationDto.getLoanType());
        loan.setInterestRate(loanAllocationDto.getInterestRate().interestRate());
        loan.setThePrincipalAmountOfTheLoan(loanAllocationDto.getThePrincipalAmountOfTheLoan());
        loan.setTotalNumberOfInstallments(loanAllocationDto.getTotalNumberOfInstallments());
        return loan;
    }

}
