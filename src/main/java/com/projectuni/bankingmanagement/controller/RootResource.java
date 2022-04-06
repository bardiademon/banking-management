package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.model.service.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class RootResource
{
    private final CustomerService customerService;

    @Inject
    public RootResource()
    {
        this.customerService = SpringConfig.newInstance(CustomerService.class);
    }

    @GET
    @Produces("text/html")
    public String root()
    {
        return "<h1 style=\"text-align: center;\">Banking Management</h1>";
    }
}