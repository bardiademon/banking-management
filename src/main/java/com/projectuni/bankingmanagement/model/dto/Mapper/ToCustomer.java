package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DTOCreateCustomer;
import com.projectuni.bankingmanagement.model.entity.Customers;

public final class ToCustomer
{
    private ToCustomer()
    {
    }

    public static Customers to(final DTOCreateCustomer dtoCreateCustomer)
    {
        final Customers customers = new Customers();
        customers.setName(dtoCreateCustomer.getName());
        customers.setFamily(dtoCreateCustomer.getFamily());
        customers.setNationalCode(dtoCreateCustomer.getNationalCode());
        customers.setPhoneNumber(dtoCreateCustomer.getPhoneNumber());
        customers.setType(dtoCreateCustomer.getType());
        customers.setDateOfBirth(dtoCreateCustomer.getDateOfBirth());
        return customers;
    }
}
