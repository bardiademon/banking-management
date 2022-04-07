package com.projectuni.bankingmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectuni.bankingmanagement.model.enums.InterestRate;
import com.projectuni.bankingmanagement.model.enums.LoanType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class LoanAllocationDto
{
    @JsonProperty("loan_type")
    private LoanType loanType;

    @JsonProperty("interest_rate")
    private InterestRate interestRate;

    @JsonProperty("deposit_id")
    private long depositId;

    // مبلغ اصلی وام
    @JsonProperty("the_principal_amount_of_the_loan")
    private double thePrincipalAmountOfTheLoan;

    // تعداد کل اقساط
    @JsonProperty("total_number_of_installments")
    private int totalNumberOfInstallments;
}
