package com.projectuni.bankingmanagement.exception;

public final class DepositIsClosedException extends Exception
{
    public DepositIsClosedException()
    {
        super("This deposit is closed");
    }
}
