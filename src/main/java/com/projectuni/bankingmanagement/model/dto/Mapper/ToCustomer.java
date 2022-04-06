package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DTOCreateCustomer;
import com.projectuni.bankingmanagement.model.entity.Customer;

public final class ToCustomer
{
    private ToCustomer()
    {
    }

    public static Customer to(final DTOCreateCustomer dtoCreateCustomer)
    {
        final Customer customer = new Customer();
        customer.setName(dtoCreateCustomer.getName());
        customer.setFamily(dtoCreateCustomer.getFamily());
        customer.setNationalCode(dtoCreateCustomer.getNationalCode());
        customer.setPhoneNumber(dtoCreateCustomer.getPhoneNumber());
        customer.setType(dtoCreateCustomer.getType());
        customer.setDateOfBirth(dtoCreateCustomer.getDateOfBirth());
        return customer;
    }
}
