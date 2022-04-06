package com.projectuni.bankingmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectuni.bankingmanagement.model.entity.Customers;
import com.projectuni.bankingmanagement.model.entity.Deposit;
import com.projectuni.bankingmanagement.model.enums.TransactionsStatus;
import com.projectuni.bankingmanagement.model.enums.TransactionsType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class DTOTransaction
{
    @JsonIgnore
    private Customers customer;

    @JsonProperty("price")
    private long price;

    @JsonProperty("transactions_status")
    private TransactionsStatus transactionsStatus;

    @JsonProperty("transactions_type")
    private TransactionsType transactionsType;

    @JsonProperty("to")
    private Deposit to;

    @JsonProperty("to")
    private Deposit from;

    @JsonProperty("transaction_description")
    private String description;
}
