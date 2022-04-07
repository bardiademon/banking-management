package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.CreateCustomerDto;
import com.projectuni.bankingmanagement.model.entity.Customers;

public final class ToCustomer
{
    private ToCustomer()
    {
    }

    public static Customers to(final CreateCustomerDto createCustomerDto)
    {
        final Customers customers = new Customers();
        customers.setName(createCustomerDto.getName());
        customers.setFamily(createCustomerDto.getFamily());
        customers.setNationalCode(createCustomerDto.getNationalCode());
        customers.setPhoneNumber(createCustomerDto.getPhoneNumber());
        customers.setType(createCustomerDto.getType());
        customers.setDateOfBirth(createCustomerDto.getDateOfBirth());
        return customers;
    }
}
