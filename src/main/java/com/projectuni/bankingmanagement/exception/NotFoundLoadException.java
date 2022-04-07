package com.projectuni.bankingmanagement.exception;

public final class NotFoundLoadException extends Exception
{
    public NotFoundLoadException(long loanId)
    {
        super("Not found loan: " + loanId);
    }
}
