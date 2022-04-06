package com.projectuni.bankingmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectuni.bankingmanagement.model.enums.DepositCurrency;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.enums.DepositType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class DTODeposit
{
    @JsonProperty("deposit_type")
    private DepositType depositType;

    @JsonProperty("customer_id")
    private long customerId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("deposit_status")
    private DepositStatus depositStatus;

    @JsonProperty("deposit_currency")
    private DepositCurrency depositCurrency;

    @JsonProperty("account_inventory")
    private long accountInventory;

    @JsonProperty("validity_start_date")
    private String validityStartDate;

    @JsonProperty("credit_expiration_date")
    private String creditExpirationDate;
}