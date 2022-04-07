package com.projectuni.bankingmanagement.exception;

public final class NotFoundLoanException extends Exception
{
    public NotFoundLoanException(long loanId)
    {
        super("Not found loan: " + loanId);
    }

    public NotFoundLoanException()
    {
        super("Not found loans");
    }
}
