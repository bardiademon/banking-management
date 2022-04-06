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
public final class DTOOpeningDeposit
{
    @JsonProperty("deposit_type")
    private DepositType depositType;

    @JsonProperty("deposit_status")
    private DepositStatus depositStatus;

    @JsonProperty("deposit_currency")
    private DepositCurrency depositCurrency;

    @JsonProperty("account_inventory")
    private long accountInventory;

    /**
     * month
     */
    @JsonProperty("credit_expiration_date")
    private int creditExpirationDate = 0;

    @JsonProperty("customers")
    private List<Integer> customerIds;
}
