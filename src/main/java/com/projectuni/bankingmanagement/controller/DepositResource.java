package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.DepositIsClosedException;
import com.projectuni.bankingmanagement.exception.InvalidAccountInventory;
import com.projectuni.bankingmanagement.exception.InvalidCreditExpirationDate;
import com.projectuni.bankingmanagement.exception.InvalidIncreaseDepositException;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.model.dto.DTOCustomer;
import com.projectuni.bankingmanagement.model.dto.DTODeposit;
import com.projectuni.bankingmanagement.model.dto.DTOOpeningDeposit;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDTOCustomer;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDTODeposit;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.service.DepositService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/deposit")
public class DepositResource
{

    private final DepositService depositService;

    public DepositResource()
    {
        depositService = SpringConfig.newInstance(DepositService.class);
    }

    @GET
    @Path("/{CUSTOMER_ID}")
    @Produces("application/json")
    public List<DTODeposit> getDeposits(@PathParam("CUSTOMER_ID") String customerIdStr)
    {
        try
        {
            return ToDTODeposit.to(depositService.getDeposits(Long.parseLong(customerIdStr)));
        }
        catch (Exception ignored)
        {
        }
        return null;
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public List<DTODeposit> getDeposits()
    {
        try
        {
            return ToDTODeposit.to(depositService.getDeposits());
        }
        catch (Exception ignored)
        {
        }
        return null;
    }

    @GET
    @Path("/get-customers/{ID_DEPOSIT}")
    @Produces("application/json")
    public List<DTOCustomer> getCustomersDeposits(@PathParam("ID_DEPOSIT") String idDepositStr)
    {
        try
        {
            return ToDTOCustomer.to(depositService.getCustomerDeposits(Integer.parseInt(idDepositStr)));
        }
        catch (Exception ignored)
        {
        }
        return null;
    }

    @POST
    @Path("/change-status/{ID_DEPOSIT}/{STATUS}")
    @Produces("application/json")
    public String getStatusDeposits(@PathParam("ID_DEPOSIT") String idDepositStr , @PathParam("STATUS") DepositStatus status)
    {

        long idDeposit;
        try
        {
            idDeposit = Integer.parseInt(idDepositStr);
        }
        catch (Exception ignored)
        {
            return "invalid deposit id";
        }

        try
        {
            depositService.changeStatus(idDeposit , status);
            return "changed";
        }
        catch (NotFoundDepositException | DepositIsClosedException e)
        {
            return e.getMessage();
        }
    }

    // Increase deposit account
    @POST
    @Path("/increase/{ID_DEPOSIT}/{AMOUNT}")
    @Produces("application/json")
    public String increaseDeposit(@PathParam("ID_DEPOSIT") String idDepositStr , @PathParam("AMOUNT") long amount)
    {

        long idDeposit;
        try
        {
            idDeposit = Integer.parseInt(idDepositStr);
        }
        catch (Exception ignored)
        {
            return "invalid deposit id";
        }

        try
        {
            depositService.increase(idDeposit , amount);
            return "increase";
        }
        catch (NotFoundDepositException | InvalidAccountInventory | InvalidIncreaseDepositException e)
        {
            return e.getMessage();
        }

    }

    @POST
    @Path("/opening")
    @Produces("application/json")
    @Consumes("application/json")
    public String openingADeposit(final DTOOpeningDeposit dtoOpeningDeposit)
    {
        try
        {
            depositService.openingDeposit(dtoOpeningDeposit);
            return "opened";
        }
        catch (NullPointerException | NotFoundCustomerException | InvalidAccountInventory | InvalidCreditExpirationDate e)
        {
            return e.getMessage();
        }
    }


}
