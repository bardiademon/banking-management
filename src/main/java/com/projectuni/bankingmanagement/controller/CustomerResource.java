package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.CannotCreateCustomerException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerNameException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerTypeException;
import com.projectuni.bankingmanagement.exception.InvalidDateOfBirthException;
import com.projectuni.bankingmanagement.exception.InvalidNationalCodeException;
import com.projectuni.bankingmanagement.model.dto.DTOCreateCustomer;
import com.projectuni.bankingmanagement.model.dto.DTOCustomer;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDtoCustomer;
import com.projectuni.bankingmanagement.model.service.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/customer")
public class CustomerResource
{
    private final CustomerService customerService;

    @Inject
    public CustomerResource()
    {
        customerService = SpringConfig.newInstance(CustomerService.class);
    }

    @POST
    @Path("/create-customer")
    @Produces("application/json")
    @Consumes("application/json")
    public String createCustomer(final DTOCreateCustomer dtoCreateCustomer)
    {
        try
        {
            customerService.createCustomer(dtoCreateCustomer);
            return "successfully!";
        }
        catch (InvalidCustomerNameException | InvalidNationalCodeException | InvalidDateOfBirthException | CannotCreateCustomerException | InvalidCustomerTypeException | InternalServerErrorException e)
        {
            return e.getMessage();
        }
    }

    @GET
    @Path("/get-customers")
    @Produces("application/json")
    public List<DTOCustomer> createCustomer()
    {
        return ToDtoCustomer.to(customerService.getCustomers());
    }

}
