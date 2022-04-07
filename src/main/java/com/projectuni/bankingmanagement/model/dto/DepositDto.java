package com.projectuni.bankingmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectuni.bankingmanagement.model.enums.DepositCurrency;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.enums.DepositType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DepositDto
{
    @JsonProperty("id")
    private long id;

    @JsonProperty("deposit_type")
    private DepositType depositType;

    @JsonProperty("deposit_status")
    private DepositStatus depositStatus;

    @JsonProperty("deposit_currency")
    private DepositCurrency depositCurrency;

    @JsonProperty("account_inventory")
    private double accountInventory;

    @JsonProperty("validity_start_date")
    private String validityStartDate;

    @JsonProperty("credit_expiration_date")
    private String creditExpirationDate;

    @JsonProperty("customers")
    private List<CustomerDto> dtoCustomers;
}
