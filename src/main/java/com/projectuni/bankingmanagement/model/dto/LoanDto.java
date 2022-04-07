package com.projectuni.bankingmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectuni.bankingmanagement.model.enums.LoanStatus;
import com.projectuni.bankingmanagement.model.enums.LoanType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class LoanDto extends LoanAllocationDto
{
    // تعداد اقساط باقیمانده
    @JsonProperty("number_of_remaining_installments")
    private int numberOfRemainingInstallments;

    // مبلغ در هر قسط
    @JsonProperty("amount_per_installment")
    private double amountPerInstallment;

    @JsonProperty("deposit")
    private DepositDto deposit;

    @JsonProperty("loan_type")
    private LoanType loanType;

    @JsonProperty("loan_status")
    private LoanStatus loanStatus;
}
