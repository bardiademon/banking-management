package com.projectuni.bankingmanagement.exception;

public final class InvalidDateOfBirthException extends Exception
{
    public InvalidDateOfBirthException(String pattern)
    {
        super("Invalid date of birth! [Valid: " + pattern + "]");
    }
}

