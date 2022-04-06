package com.projectuni.bankingmanagement.exception;

public final class InvalidNationalCodeException extends Exception
{
    public InvalidNationalCodeException()
    {
        this("Invalid national code!");
    }

    public InvalidNationalCodeException(final String message)
    {
        super(message);
    }
}
