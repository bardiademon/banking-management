package com.projectuni.bankingmanagement.exception;

public final class NotFoundDepositException extends Exception
{
    public NotFoundDepositException()
    {
        super("Not found Deposit");
    }

    public NotFoundDepositException(final long depositId)
    {
        super("Not found Deposit: " + depositId);
    }
}
