package com.projectuni.bankingmanagement.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class RootResource
{
    @GET
    @Produces("text/html")
    public String root()
    {
        return "<h1 style=\"text-align: center;\">Banking Management</h1>";
    }
}