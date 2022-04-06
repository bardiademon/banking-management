package com.projectuni.bankingmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToCustomer;
import com.projectuni.bankingmanagement.model.enums.CustomerType;
import com.projectuni.bankingmanagement.model.service.CustomerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public final class DTOCreateCustomer
{
    @JsonProperty("name")
    private String name;

    @JsonProperty("family")
    private String family;

    @JsonProperty("national_code")
    private int nationalCode;

    @JsonProperty("date_of_birth")
    private String dateOfBirthStr;

    @JsonProperty("type")
    private CustomerType type;

    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * This is a property for the mapper class that converts to Customer
     *
     * @see CustomerService#createCustomer(DTOCreateCustomer)
     * @see ToCustomer#to(DTOCreateCustomer)
     */
    @JsonIgnore
    private Date dateOfBirth;

}
