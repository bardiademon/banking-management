package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.model.dto.LoanDto;
import com.projectuni.bankingmanagement.model.service.LoanService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
    public String loanAllocation(final LoanDto loanDto)
    {
        try
        {
            loanService.loanAllocation(loanDto);
            return "done!";
        }
        catch (NotFoundDepositException | NullPointerException | InternalServerErrorException e)
        {
            return e.getMessage();
        }
    }
}
