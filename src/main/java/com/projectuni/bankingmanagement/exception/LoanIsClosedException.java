package com.projectuni.bankingmanagement.exception;

public class LoanIsClosedException extends Exception
{
    public LoanIsClosedException(long loanId)
    {
        super("This loan is closed: " + loanId);
    }
}
