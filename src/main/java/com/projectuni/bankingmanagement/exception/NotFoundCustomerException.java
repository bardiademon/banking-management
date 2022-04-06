package com.projectuni.bankingmanagement.exception;

public final class NotFoundCustomerException extends Exception
{
    public NotFoundCustomerException()
    {
        super("Customer not found!");
    }
}
