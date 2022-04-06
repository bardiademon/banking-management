package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DTOOpeningDeposit;
import com.projectuni.bankingmanagement.model.entity.Deposit;

import java.time.LocalDateTime;

public final class ToDeposit
{
    private ToDeposit()
    {

    }

    public static Deposit to(final DTOOpeningDeposit dtoOpeningDeposit)
    {
        final Deposit deposit = new Deposit();
        deposit.setDepositType(dtoOpeningDeposit.getDepositType());
        deposit.setDepositStatus(dtoOpeningDeposit.getDepositStatus());
        deposit.setDepositCurrency(dtoOpeningDeposit.getDepositCurrency());
        deposit.setCreditExpirationDate(LocalDateTime.now().plusMonths(dtoOpeningDeposit.getCreditExpirationDate()));
        deposit.setAccountInventory(dtoOpeningDeposit.getAccountInventory());
        deposit.setValidityStartDate(LocalDateTime.now());
        return deposit;
    }
}
