package com.projectuni.bankingmanagement.exception;

public final class FoundDepositCustomer extends Exception
{
    public FoundDepositCustomer()
    {
        super("This customer has a deposit");
    }
}
