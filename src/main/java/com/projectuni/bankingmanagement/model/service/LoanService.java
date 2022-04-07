package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.model.dto.LoanDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToLoan;
import com.projectuni.bankingmanagement.model.entity.Deposit;
import com.projectuni.bankingmanagement.model.entity.Loan;
import com.projectuni.bankingmanagement.model.enums.LoanStatus;
import com.projectuni.bankingmanagement.model.repository.LoanRepository;
import org.springframework.stereotype.Repository;

import javax.ws.rs.InternalServerErrorException;

@Repository
public record LoanService(LoanRepository loanRepository , DepositService depositService)
{

    public void loanAllocation(final LoanDto loanDto) throws NullPointerException, NotFoundDepositException, InternalServerErrorException
    {
        if (loanDto != null && loanDto.getThePrincipalAmountOfTheLoan() > 0 && loanDto.getTotalNumberOfInstallments() > 0)
        {
            if (loanDto.getLoanType() != null)
            {
                if (loanDto.getInterestRate() != null)
                {
                    final Deposit depositById = depositService.getDepositById(loanDto.getDepositId());

                    Loan loan = ToLoan.to(loanDto);
                    loan.setAmountPerInstallment(calculateTheAmountOfEachInstallment(loan.getThePrincipalAmountOfTheLoan() , loan.getInterestRate() , loan.getTotalNumberOfInstallments()));
                    loan.setNumberOfRemainingInstallments(loan.getTotalNumberOfInstallments());
                    loan.setDeposit(depositById);
                    loan.setLoanStatus(LoanStatus.OPEN);

                    loan = loanRepository.save(loan);

                    if (loan.getId() <= 0) throw new InternalServerErrorException("Cannot loan allocation");

                }
                else throw new NullPointerException("InterestRate is null ");
            }
            else throw new NullPointerException("LoanType is null ");
        }
        else throw new NullPointerException("Request is null");
    }

    public double calculateTheAmountOfEachInstallment(final double thePrincipalAmountOfTheLoan , final int interestRate , final int totalNumberOfInstallments)
    {
        return ((profitCalculation(thePrincipalAmountOfTheLoan , interestRate , totalNumberOfInstallments) + interestRate) / totalNumberOfInstallments);
    }

    public double profitCalculation(final double thePrincipalAmountOfTheLoan , final int interestRate , final int totalNumberOfInstallments)
    {
        return ((thePrincipalAmountOfTheLoan + interestRate) + (totalNumberOfInstallments + 1)) / 2400;
    }
}
