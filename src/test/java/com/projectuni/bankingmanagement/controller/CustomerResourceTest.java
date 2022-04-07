package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.CannotCreateCustomerException;
import com.projectuni.bankingmanagement.exception.FoundDepositCustomer;
import com.projectuni.bankingmanagement.exception.InvalidCustomerNameException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerTypeException;
import com.projectuni.bankingmanagement.exception.InvalidDateOfBirthException;
import com.projectuni.bankingmanagement.exception.InvalidNationalCodeException;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.model.dto.CustomerDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToCustomerDto;
import com.projectuni.bankingmanagement.model.dto.SearchCustomerDto;
import com.projectuni.bankingmanagement.model.enums.CustomerType;
import com.projectuni.bankingmanagement.model.service.CustomersService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

class CustomerResourceTest
{

    private static CustomersService customersService;

    @BeforeAll
    public static void before()
    {
        SpringConfig.config();

        customersService = SpringConfig.newInstance(CustomersService.class);
    }

    @Test
    void createCustomer()
    {
        final CustomerDto customerDto = new CustomerDto();
        customerDto.setStatus(true);

        final Date in = new Date();
        LocalDateTime year25 = LocalDateTime.ofInstant(in.toInstant() , ZoneId.systemDefault()).minusYears(25);
        customerDto.setDateOfBirth(Date.from(year25.atZone(ZoneId.systemDefault()).toInstant()));

        customerDto.setFamily("MY_FAMILY");
        customerDto.setName("MY_NAME");
        customerDto.setDateOfBirthStr("25/06/1375");
        customerDto.setNationalCode(264989489);
        customerDto.setPhoneNumber("989170000000");
        customerDto.setType(CustomerType.A_REAL);

        try
        {
            customersService.createCustomer(customerDto);
            System.out.println("done");
        }
        catch (InvalidCustomerNameException | InvalidNationalCodeException | InvalidDateOfBirthException | CannotCreateCustomerException | InvalidCustomerTypeException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void getCustomers()
    {
        customersService.getCustomers();
    }

    @Test
    void searchCustomer()
    {
        final SearchCustomerDto searchCustomerDto = new SearchCustomerDto();
        searchCustomerDto.setName("MY_NAME");
        searchCustomerDto.setType(CustomerType.A_REAL);
        searchCustomerDto.setNationalCode(264989489);

        try
        {
            System.out.println(ToCustomerDto.to(customersService.getCustomers(searchCustomerDto)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void changeCustomerStatus()
    {
        try
        {
            customersService.changeCustomerStatus(2 , false);
            System.out.println("done!");
        }
        catch (NotFoundCustomerException e)
        {
            e.printStackTrace();
        }

    }

    @Test
    void deleteCustomer()
    {
        try
        {
            customersService.deleteCustomer(256);
            System.out.println("deleted");
        }
        catch (NotFoundCustomerException | FoundDepositCustomer e)
        {
            e.printStackTrace();
        }
    }

}