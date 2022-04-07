package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.OpeningDepositDto;
import com.projectuni.bankingmanagement.model.entity.Deposit;

import java.time.LocalDateTime;

public final class ToDeposit
{
    private ToDeposit()
    {

    }

    public static Deposit to(final OpeningDepositDto openingDepositDto)
    {
        final Deposit deposit = new Deposit();
        deposit.setDepositType(openingDepositDto.getDepositType());
        deposit.setDepositStatus(openingDepositDto.getDepositStatus());
        deposit.setDepositCurrency(openingDepositDto.getDepositCurrency());
        deposit.setCreditExpirationDate(LocalDateTime.now().plusMonths(openingDepositDto.getCreditExpirationDate()));
        deposit.setAccountInventory(openingDepositDto.getAccountInventory());
        deposit.setValidityStartDate(LocalDateTime.now());
        return deposit;
    }
}
