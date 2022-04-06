package com.projectuni.bankingmanagement.exception;

public final class InvalidCustomerNameException extends Exception
{

    public InvalidCustomerNameException(final String message , final boolean isName)
    {
        super((isName ? "This name {" + message + "} is invalid!" : message));
    }
}
