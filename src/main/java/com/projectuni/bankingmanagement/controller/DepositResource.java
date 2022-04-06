package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.InvalidAccountInventory;
import com.projectuni.bankingmanagement.exception.InvalidCreditExpirationDate;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.model.dto.DTODeposit;
import com.projectuni.bankingmanagement.model.dto.DTOOpeningDeposit;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDTODeposit;
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
