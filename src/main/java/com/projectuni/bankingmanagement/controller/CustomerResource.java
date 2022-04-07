package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.CannotCreateCustomerException;
import com.projectuni.bankingmanagement.exception.FoundDepositCustomer;
import com.projectuni.bankingmanagement.exception.InvalidCustomerNameException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerTypeException;
import com.projectuni.bankingmanagement.exception.InvalidDateOfBirthException;
import com.projectuni.bankingmanagement.exception.InvalidNationalCodeException;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.model.dto.CreateCustomerDto;
import com.projectuni.bankingmanagement.model.dto.CustomerDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToCustomerDto;
import com.projectuni.bankingmanagement.model.dto.SearchCustomerDto;
import com.projectuni.bankingmanagement.model.service.CustomersService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
    @Path("/create")
    @Produces("application/json")
    @Consumes("application/json")
    public String createCustomer(final CreateCustomerDto createCustomerDto)
    {
        try
        {
            customersService.createCustomer(createCustomerDto);
            return "successfully!";
        }
        catch (InvalidCustomerNameException | InvalidNationalCodeException | InvalidDateOfBirthException | CannotCreateCustomerException | InvalidCustomerTypeException | InternalServerErrorException e)
        {
            return e.getMessage();
        }
    }

    @GET
    @Path("/get")
    @Produces("application/json")
    public List<CustomerDto> getCustomers()
    {
        return ToCustomerDto.to(customersService.getCustomers());
    }

    @GET
    @Path("/search")
    @Produces("application/json")
    @Consumes("application/json")
    public List<CustomerDto> searchCustomer(final SearchCustomerDto searchCustomerDto)
    {
        return ToCustomerDto.to(customersService.getCustomers(searchCustomerDto));
    }

    @GET
    @Path("/change-status/{ID}/{STATUS}")
    @Consumes("application/json")
    public String changeCustomerStatus(@PathParam("STATUS") String statusStr , @PathParam("ID") String idStr)
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

    @DELETE
    @Path("/{CUSTOMER_ID}")
    @Consumes("application/json")
    public String deleteCustomer(final SearchCustomerDto searchCustomerDto , @PathParam("CUSTOMER_ID") String idStr)
    {
        try
        {
            int customerId;
            try
            {
                customerId = Integer.parseInt(idStr);
            }
            catch (Exception e)
            {
                return "invalid customer id";
            }

            customersService.deleteCustomer(customerId);
            return "deleted";
        }
        catch (NotFoundCustomerException | FoundDepositCustomer e)
        {
            return e.getMessage();
        }
    }

}
