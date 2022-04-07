package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.exception.NotFoundTransactionsException;
import com.projectuni.bankingmanagement.model.dto.DTOTransaction;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDTOTransaction;
import com.projectuni.bankingmanagement.model.service.DepositService;
import com.projectuni.bankingmanagement.model.service.TransactionsService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("/transactions")
public class TransactionsResource
{

    private final TransactionsService transactionsService;
    private final DepositService depositService;

    @Inject
    public TransactionsResource()
    {
        transactionsService = SpringConfig.newInstance(TransactionsService.class);
        depositService = SpringConfig.newInstance(DepositService.class);
    }

    @GET
    @Path("/{DEPOSIT_ID}")
    @Produces("application/json")
    public List<DTOTransaction> getDepositTransactions(@PathParam("DEPOSIT_ID") long depositId)
    {
        try
        {
            return ToDTOTransaction.to(transactionsService.depositTransactions(depositId , depositService));
        }
        catch (NotFoundTransactionsException | NotFoundDepositException ignored)
        {
        }
        return new ArrayList<>();
    }
}
