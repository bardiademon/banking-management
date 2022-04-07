package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DepositDto;
import com.projectuni.bankingmanagement.model.entity.Customers;
import com.projectuni.bankingmanagement.model.entity.Deposit;

import java.util.ArrayList;
import java.util.List;

public final class ToDepositDto
{
    private ToDepositDto()
    {
    }

    public static List<DepositDto> to(final List<Deposit> deposits)
    {
        final List<DepositDto> depositDtos = new ArrayList<>();

        if (deposits != null && deposits.size() > 0)
        {
            for (final Deposit deposit : deposits) depositDtos.add(to(deposit));
        }

        return depositDtos;
    }

    public static DepositDto to(Deposit deposit)
    {
        if (deposit != null)
        {
            final DepositDto depositDto = new DepositDto();
            depositDto.setId(deposit.getId());
            depositDto.setDepositStatus(deposit.getDepositStatus());
            depositDto.setDepositType(deposit.getDepositType());
            depositDto.setDepositCurrency(deposit.getDepositCurrency());
            depositDto.setAccountInventory(deposit.getAccountInventory());
            depositDto.setCreditExpirationDate(deposit.getCreditExpirationDate().toString());
            depositDto.setValidityStartDate(deposit.getValidityStartDate().toString());

            final List<Customers> customers = deposit.getCustomers();
            depositDto.setDtoCustomers(ToCustomerDto.to(customers));

            return depositDto;
        }

        return null;
    }
}
