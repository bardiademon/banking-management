package com.projectuni.bankingmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectuni.bankingmanagement.model.enums.CustomerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class DTOSearchCustomer
{
    @JsonProperty("name")
    private String name;

    @JsonProperty("national_code")
    private int nationalCode;

    @JsonProperty("type")
    private CustomerType type;
}
