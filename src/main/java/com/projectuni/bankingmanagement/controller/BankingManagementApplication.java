package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class BankingManagementApplication extends Application
{
    public BankingManagementApplication()
    {
        super();
        SpringConfig.config();
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(RootResource.class);
        classes.add(CustomerResource.class);
        classes.add(DepositResource.class);
        classes.add(TransactionsResource.class);
        return classes;
    }
}