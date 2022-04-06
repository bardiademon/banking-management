package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DTOCustomer;
import com.projectuni.bankingmanagement.model.entity.Customer;
import com.projectuni.bankingmanagement.other.DateParser;

import java.util.ArrayList;
import java.util.List;

public class ToDtoCustomer
{
    public static List<DTOCustomer> to(List<Customer> customers)
    {
        if (customers != null && customers.size() > 0)
        {
            final List<DTOCustomer> dtoCustomers = new ArrayList<>();
            for (final Customer customer : customers) dtoCustomers.add(to(customer));
            return dtoCustomers;
        }

        return null;
    }

    public static DTOCustomer to(final Customer customer)
    {
        final DTOCustomer dtoCustomer = new DTOCustomer();
        dtoCustomer.setName(customer.getName());
        dtoCustomer.setFamily(customer.getFamily());
        dtoCustomer.setNationalCode(customer.getNationalCode());
        dtoCustomer.setDateOfBirthStr(DateParser.toString(customer.getDateOfBirth()));
        dtoCustomer.setPhoneNumber(customer.getPhoneNumber());
        dtoCustomer.setType(customer.getType());
        return dtoCustomer;
    }
}
