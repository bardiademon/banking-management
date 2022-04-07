package com.projectuni.bankingmanagement.controller;

import com.projectuni.bankingmanagement.config.SpringConfig;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.AcceptHeaderApiListingResource;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.BaseApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class BankingManagementApplication extends Application
{
    public BankingManagementApplication()
    {
        SpringConfig.config();

        final BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setTitle("Banking management api");
        beanConfig.setBasePath("/banking-management/api");
        beanConfig.setResourcePackage("com.projectuni.bankingmanagement.controller");
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        final Set<Class<?>> resources = new HashSet<>();
        resources.add(RootResource.class);
        resources.add(CustomerResource.class);
        resources.add(DepositResource.class);
        resources.add(TransactionsResource.class);
        resources.add(LoanResource.class);

        // Swagger
        resources.add(ApiListingResource.class);
        resources.add(AcceptHeaderApiListingResource.class);
        resources.add(SwaggerSerializers.class);
        resources.add(BaseApiListingResource.class);

        return resources;
    }
}