package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.exception.InvalidAccountInventory;
import com.projectuni.bankingmanagement.exception.InvalidWithdrawalDepositException;
import com.projectuni.bankingmanagement.exception.InventoryIsNotEnoughException;
import com.projectuni.bankingmanagement.exception.LoanIsClosedException;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.exception.NotFoundLoanException;
import com.projectuni.bankingmanagement.model.dto.LoanAllocationDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToLoan;
import com.projectuni.bankingmanagement.model.entity.Deposit;
import com.projectuni.bankingmanagement.model.entity.Loan;
import com.projectuni.bankingmanagement.model.enums.LoanStatus;
import com.projectuni.bankingmanagement.model.repository.LoanRepository;
import org.springframework.stereotype.Repository;

import javax.ws.rs.InternalServerErrorException;
import java.util.List;
import java.util.Optional;

@Repository
public record LoanService(LoanRepository loanRepository , DepositService depositService)
{

    public void loanAllocation(final LoanAllocationDto loanAllocationDto) throws NullPointerException, NotFoundDepositException, InternalServerErrorException
    {
        if (loanAllocationDto != null && loanAllocationDto.getThePrincipalAmountOfTheLoan() > 0 && loanAllocationDto.getTotalNumberOfInstallments() > 0)
        {
            if (loanAllocationDto.getLoanType() != null)
            {
                if (loanAllocationDto.getInterestRate() != null)
                {
                    final Deposit depositById = depositService.getDepositById(loanAllocationDto.getDepositId());

                    Loan loan = ToLoan.to(loanAllocationDto);
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
        return ((thePrincipalAmountOfTheLoan * interestRate) * (totalNumberOfInstallments + 1)) / 2400;
    }

    public void loanPayments(final long loanId) throws NotFoundLoanException, LoanIsClosedException, InventoryIsNotEnoughException, InvalidAccountInventory, NotFoundDepositException, InvalidWithdrawalDepositException
    {
        final Optional<Loan> loanById = loanRepository.findById(loanId);
        if (loanById.isPresent())
        {
            final Loan loan = loanById.get();
            if (!loan.getLoanStatus().equals(LoanStatus.CLOSED))
            {
                final double amountPerInstallment = loan.getAmountPerInstallment();

                final Deposit deposit = loan.getDeposit();

                if (deposit.getAccountInventory() >= amountPerInstallment)
                {
                    depositService.withdrawal(deposit.getId() , amountPerInstallment);

                    final int numberOfRemainingInstallments = loan.getNumberOfRemainingInstallments();

                    loan.setNumberOfRemainingInstallments(numberOfRemainingInstallments - 1);

                    if (loan.getNumberOfRemainingInstallments() <= 0) loan.setLoanStatus(LoanStatus.CLOSED);
                    else loan.setLoanStatus(LoanStatus.PAYING);

                    loanRepository.save(loan);
                }
                else throw new InventoryIsNotEnoughException();
            }
            else throw new LoanIsClosedException(loanId);
        }
        else throw new NotFoundLoanException(loanId);
    }

    public List<Loan> getLoans() throws NotFoundLoanException
    {
        final List<Loan> loans = loanRepository.findAll();
        if (loans.size() > 0) return loans;
        else throw new NotFoundLoanException();
    }
}
