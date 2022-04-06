package com.projectuni.bankingmanagement.other;

public final class Str
{
    private Str()
    {
    }


    public static boolean notEmpty(final String val)
    {
        return (!isEmpty(val));
    }

    public static boolean isEmpty(final String val)
    {
        return (val == null || val.isEmpty());
    }
}
