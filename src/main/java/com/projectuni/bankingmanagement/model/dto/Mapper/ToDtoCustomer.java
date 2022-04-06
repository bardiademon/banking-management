package com.projectuni.bankingmanagement.model.dto.Mapper;

import com.projectuni.bankingmanagement.model.dto.DTOCustomer;
import com.projectuni.bankingmanagement.model.entity.Customers;
import com.projectuni.bankingmanagement.other.DateParser;

import java.util.ArrayList;
import java.util.List;

public class ToDtoCustomer
{
    public static List<DTOCustomer> to(List<Customers> customers)
    {
        if (customers != null && customers.size() > 0)
        {
            final List<DTOCustomer> dtoCustomers = new ArrayList<>();
            for (final Customers customer : customers) dtoCustomers.add(to(customer));
            return dtoCustomers;
        }

        return null;
    }

    public static DTOCustomer to(final Customers customers)
    {
        System.out.println("Greg");
        final DTOCustomer dtoCustomer = new DTOCustomer();
        dtoCustomer.setName(customers.getName());
        dtoCustomer.setFamily(customers.getFamily());
        dtoCustomer.setStatus(customers.isStatus());
        dtoCustomer.setNationalCode(customers.getNationalCode());
        dtoCustomer.setDateOfBirthStr(DateParser.toString(customers.getDateOfBirth()));
        dtoCustomer.setPhoneNumber(customers.getPhoneNumber());
        dtoCustomer.setType(customers.getType());
        return dtoCustomer;
    }
}
