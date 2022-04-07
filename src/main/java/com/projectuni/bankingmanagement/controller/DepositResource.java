package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.DepositIsClosedException;
import com.projectuni.bankingmanagement.exception.InvalidAccountInventory;
import com.projectuni.bankingmanagement.exception.InvalidCreditExpirationDate;
import com.projectuni.bankingmanagement.exception.InvalidIncreaseDepositException;
import com.projectuni.bankingmanagement.exception.InvalidWithdrawalDepositException;
import com.projectuni.bankingmanagement.exception.InventoryIsNotEnoughException;
import com.projectuni.bankingmanagement.exception.MoneyTransferException;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.model.dto.CustomerDto;
import com.projectuni.bankingmanagement.model.dto.DepositDto;
import com.projectuni.bankingmanagement.model.dto.OpeningDepositDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToCustomerDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDepositDto;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.service.DepositService;
import io.swagger.annotations.Api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("/deposit")
@Api(value = "/deposit")
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
    public List<DepositDto> getDeposits(@PathParam("CUSTOMER_ID") String customerIdStr)
    {
        try
        {
            return ToDepositDto.to(depositService.getDeposits(Long.parseLong(customerIdStr)));
        }
        catch (Exception ignored)
        {
        }
        return null;
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public List<DepositDto> getDeposits()
    {
        try
        {
            return ToDepositDto.to(depositService.getDeposits());
        }
        catch (Exception ignored)
        {
        }
        return null;
    }

    @GET
    @Path("/get-customers/{ID_DEPOSIT}")
    @Produces("application/json")
    public List<CustomerDto> getCustomersDeposits(@PathParam("ID_DEPOSIT") String idDepositStr)
    {
        try
        {
            return ToCustomerDto.to(depositService.getCustomerDeposits(Integer.parseInt(idDepositStr)));
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
    public String increaseDeposit(@PathParam("ID_DEPOSIT") String idDepositStr , @PathParam("AMOUNT") double amount)
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
            long transactionId = depositService.increase(idDeposit , amount);
            return "increase: " + transactionId; // issue tracking
        }
        catch (NotFoundDepositException | InvalidAccountInventory | InvalidIncreaseDepositException | NullPointerException | InternalServerErrorException e)
        {
            return e.getMessage();
        }

    }

    @GET
    @Path("/withdrawal/{ID_DEPOSIT}/{AMOUNT}")
    @Produces("application/json")
    public String withdrawalDeposit(@PathParam("ID_DEPOSIT") String idDepositStr , @PathParam("AMOUNT") double amount)
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
            long transactionId = depositService.withdrawal(idDeposit , amount);
            return "withdrawal: " + transactionId; // issue tracking
        }
        catch (NotFoundDepositException | InvalidAccountInventory | InvalidWithdrawalDepositException | InventoryIsNotEnoughException | NullPointerException | InternalServerErrorException e)
        {
            return e.getMessage();
        }

    }

    @POST
    @Path("/opening")
    @Produces("application/json")
    @Consumes("application/json")
    public String openingADeposit(final OpeningDepositDto openingDepositDto)
    {
        try
        {
            depositService.openingDeposit(openingDepositDto);
            return "opened";
        }
        catch (NullPointerException | NotFoundCustomerException | InvalidAccountInventory | InvalidCreditExpirationDate e)
        {
            return e.getMessage();
        }
    }

    @POST
    @Path("/money-transfer/{FROM}/{TO}/{PRICE}")
    @Produces("application/json")
    public Map<String, Object> moneyTransfer(@PathParam("FROM") long fromDeposit , @PathParam("TO") long toDeposit , @PathParam("PRICE") double price)
    {
        final Map<String, Object> response = new LinkedHashMap<>();

        try
        {
            final long[] issueTracking = depositService.moneyTransfer(fromDeposit , toDeposit , price);

            response.put("result" , "done");
            response.put("from_issue_tracking" , issueTracking[0]);
            response.put("to_issue_tracking" , issueTracking[1]);
        }
        catch (MoneyTransferException | NullPointerException | InvalidAccountInventory | NotFoundDepositException | InventoryIsNotEnoughException | InvalidWithdrawalDepositException | InvalidIncreaseDepositException e)
        {
            response.put("result" , e.getMessage());
        }
        return response;
    }

    @GET
    @Path("/deposit-balance/{DEPOSIT_ID}")
    @Produces("application/json")
    public String getDepositBalance(@PathParam("DEPOSIT_ID") long depositId)
    {
        try
        {
            return depositService.getDepositBalance(depositId);
        }
        catch (NotFoundDepositException e)
        {
            return e.getMessage();
        }
    }

}
