package com.projectuni.bankingmanagement.exception;

public final class InvalidIncreaseDepositException extends Exception
{
    public InvalidIncreaseDepositException()
    {
        super("Possibility to deposit to the account only if the deposit is open or withdrawal is blocked");
    }
}
