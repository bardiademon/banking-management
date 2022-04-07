package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.InvalidAccountInventory;
import com.projectuni.bankingmanagement.exception.InvalidWithdrawalDepositException;
import com.projectuni.bankingmanagement.exception.InventoryIsNotEnoughException;
import com.projectuni.bankingmanagement.exception.LoanIsClosedException;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.exception.NotFoundLoanException;
import com.projectuni.bankingmanagement.model.dto.LoanAllocationDto;
import com.projectuni.bankingmanagement.model.dto.LoanDto;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToLoanDto;
import com.projectuni.bankingmanagement.model.service.LoanService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/loan")
public class LoanResource
{

    private final LoanService loanService;

    @Inject
    public LoanResource()
    {
        loanService = SpringConfig.newInstance(LoanService.class);
    }

    @POST
    @Path("/loan-allocation")
    @Produces("text/plain")
    @Consumes("application/json")
    public String loanAllocation(final LoanAllocationDto loanAllocationDto)
    {
        try
        {
            loanService.loanAllocation(loanAllocationDto);
            return "done!";
        }
        catch (NotFoundDepositException | NullPointerException | InternalServerErrorException e)
        {
            return e.getMessage();
        }
    }

    @POST
    @Path("/loan-payments/{LOAN_ID}")
    @Produces("text/plain")
    public String loanPayments(@PathParam("LOAN_ID") long loanId)
    {
        try
        {
            loanService.loanPayments(loanId);
            return "done!";
        }
        catch (NotFoundLoanException | LoanIsClosedException | InventoryIsNotEnoughException | InvalidAccountInventory | NotFoundDepositException | InvalidWithdrawalDepositException e)
        {
            return e.getMessage();
        }
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public List<LoanDto> getLoans()
    {
        try
        {
            return ToLoanDto.to(loanService.getLoans());
        }
        catch (NotFoundLoanException ignored)
        {
        }
        return null;
    }
}
