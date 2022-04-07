package com.projectuni.bankingmanagement.exception;

public final class NotFoundTransactionsException extends Exception
{
    public NotFoundTransactionsException()
    {
        super("Not found transactions!");
    }
}
