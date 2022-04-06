package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DTODeposit;
import com.projectuni.bankingmanagement.model.entity.Customers;
import com.projectuni.bankingmanagement.model.entity.Deposit;

import java.util.ArrayList;
import java.util.List;

public final class ToDTODeposit
{
    private ToDTODeposit()
    {
    }

    public static List<DTODeposit> to(final List<Deposit> deposits)
    {
        final List<DTODeposit> dtoDeposits = new ArrayList<>();

        if (deposits != null && deposits.size() > 0)
        {
            for (final Deposit deposit : deposits) dtoDeposits.add(to(deposit));
        }

        return dtoDeposits;
    }

    public static DTODeposit to(Deposit deposit)
    {
        if (deposit != null)
        {
            final DTODeposit dtoDeposit = new DTODeposit();
            dtoDeposit.setDepositStatus(deposit.getDepositStatus());
            dtoDeposit.setDepositType(deposit.getDepositType());
            dtoDeposit.setDepositCurrency(deposit.getDepositCurrency());
            dtoDeposit.setAccountInventory(deposit.getAccountInventory());
            dtoDeposit.setCreditExpirationDate(deposit.getCreditExpirationDate().toString());
            dtoDeposit.setValidityStartDate(deposit.getValidityStartDate().toString());

            final Customers customer = deposit.getCustomer();
            dtoDeposit.setCustomerId(customer.getId());
            dtoDeposit.setCustomerName(customer.getName());

            return dtoDeposit;
        }

        return null;
    }
}
