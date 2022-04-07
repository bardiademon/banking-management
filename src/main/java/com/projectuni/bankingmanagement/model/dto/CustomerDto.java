package com.projectuni.bankingmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomerDto extends CreateCustomerDto
{

    @JsonProperty("id")
    private long id;

    @JsonProperty("status")
    private boolean status;
}
