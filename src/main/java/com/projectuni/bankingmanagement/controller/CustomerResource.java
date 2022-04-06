package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.CannotCreateCustomerException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerNameException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerTypeException;
import com.projectuni.bankingmanagement.exception.InvalidDateOfBirthException;
import com.projectuni.bankingmanagement.exception.InvalidNationalCodeException;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.model.dto.DTOCreateCustomer;
import com.projectuni.bankingmanagement.model.dto.DTOCustomer;
import com.projectuni.bankingmanagement.model.dto.DTOSearchCustomer;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDtoCustomer;
import com.projectuni.bankingmanagement.model.service.CustomersService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/customer")
public class CustomerResource
{
    private final CustomersService customersService;

    @Inject
    public CustomerResource()
    {
        customersService = SpringConfig.newInstance(CustomersService.class);
    }

    @POST
    @Path("/create-customer")
    @Produces("application/json")
    @Consumes("application/json")
    public String createCustomer(final DTOCreateCustomer dtoCreateCustomer)
    {
        try
        {
            customersService.createCustomer(dtoCreateCustomer);
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
    public List<DTOCustomer> getCustomers()
    {
        return ToDtoCustomer.to(customersService.getCustomers());
    }

    @GET
    @Path("/search-customers")
    @Produces("application/json")
    @Consumes("application/json")
    public List<DTOCustomer> searchCustomer(final DTOSearchCustomer dtoSearchCustomer)
    {
        return ToDtoCustomer.to(customersService.getCustomers(dtoSearchCustomer));
    }

    @GET
    @Path("/change-status/{ID}/{STATUS}")
    @Consumes("application/json")
    public String searchCustomer(final DTOSearchCustomer dtoSearchCustomer , @PathParam("STATUS") String statusStr , @PathParam("ID") String idStr)
    {
        String response;
        try
        {
            if (statusStr.equals("true") || statusStr.equals("false"))
            {
                final boolean status = Boolean.parseBoolean(statusStr);
                try
                {
                    final long id = Long.parseLong(idStr);

                    try
                    {
                        customersService.changeCustomerStatus(id , status);
                        response = "changed";
                    }
                    catch (NotFoundCustomerException e)
                    {
                        response = e.getMessage();
                    }
                }
                catch (Exception e)
                {
                    response = "invalid id";
                }
            }
            else throw new Exception();
        }
        catch (Exception e)
        {
            response = "invalid status";
        }
        return response;
    }

}
