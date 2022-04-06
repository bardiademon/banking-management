package com.projectuni.bankingmanagement.exception;

public final class InvalidCustomerTypeException extends Exception
{
    public InvalidCustomerTypeException()
    {
        super("This customer type invalid!");
    }
}
