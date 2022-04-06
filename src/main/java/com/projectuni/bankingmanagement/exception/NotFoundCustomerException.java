package com.projectuni.bankingmanagement.exception;

public final class NotFoundCustomerException extends Exception
{
    public NotFoundCustomerException()
    {
        super("Customer not found!");
    }

    public NotFoundCustomerException(final long customerId)
    {
        super("Customer{" + customerId + "} not found!");
    }
}
