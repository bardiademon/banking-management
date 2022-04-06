package com.projectuni.bankingmanagement.other;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateParser
{
    private DateParser()
    {
    }

    public static Date pars(final String text)
    {
        return pars(text , "yyyy-MM-dd'T'HH:mm");
    }

    public static Date pars(final String text , final String pattern)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try
        {
            return dateFormat.parse(text);
        }
        catch (Exception ignored)
        {
        }
        return null;
    }

    public static String toString(final Date date)
    {
        if (date != null)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                return dateFormat.format(date);
            }
            catch (Exception ignored)
            {
            }
        }

        return "0000-00-00";
    }
}
