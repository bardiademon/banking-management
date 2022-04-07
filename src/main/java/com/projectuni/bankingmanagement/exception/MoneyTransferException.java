package com.projectuni.bankingmanagement.exception;

public final class MoneyTransferException extends Exception
{
    public MoneyTransferException()
    {
        super("Money transfer only if: both deposits are open or the first deposit is Blocked Deposit and the second deposit is blocked withdrawal");
    }
}
