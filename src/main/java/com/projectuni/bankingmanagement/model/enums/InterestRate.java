package com.projectuni.bankingmanagement.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(fluent = true)
public enum InterestRate
{
    PERCENT_4(4),
    PERCENT_18(18),
    PERCENT_24(24);

    @Getter
    private final int interestRate;
}
