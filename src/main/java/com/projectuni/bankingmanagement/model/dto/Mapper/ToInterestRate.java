package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.enums.InterestRate;

public final class ToInterestRate
{
    private ToInterestRate()
    {
    }

    public static InterestRate to(final int value)
    {
        return switch (value)
                {
                    case 4 -> InterestRate.PERCENT_4;
                    case 18 -> InterestRate.PERCENT_18;
                    case 24 -> InterestRate.PERCENT_24;
                    default -> throw new IllegalStateException("Unexpected value: " + value);
                };
    }
}
