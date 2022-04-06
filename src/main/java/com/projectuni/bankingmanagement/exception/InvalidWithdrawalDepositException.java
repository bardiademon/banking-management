package com.projectuni.bankingmanagement.exception;

public final class InvalidWithdrawalDepositException extends Exception
{
    public InvalidWithdrawalDepositException()
    {
        super("Withdrawal from the deposit account only if the account is open or the deposit is blocked");
    }
}
