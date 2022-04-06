package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.model.dto.DTODeposit;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDTODeposit;
import com.projectuni.bankingmanagement.model.entity.Customers;
import com.projectuni.bankingmanagement.model.entity.Deposit;
import com.projectuni.bankingmanagement.model.enums.DepositCurrency;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.enums.DepositType;
import com.projectuni.bankingmanagement.model.service.DepositService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.LocalDateTime;
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
    public String openingADeposit()
    {
        List<Customers> customers = depositService.customersRepository().findAll();

        Deposit deposit = new Deposit();
        deposit.setCustomers(customers);
        deposit.setDepositType(DepositType.LONG_TIME);
        deposit.setDepositStatus(DepositStatus.OPEN);
        deposit.setDepositCurrency(DepositCurrency.RIAL);
        deposit.setCreditExpirationDate(LocalDateTime.now().plusYears(1));
        deposit.setValidityStartDate(LocalDateTime.now());
        depositService.depositRepository().save(deposit);

        return null;
    }


}
