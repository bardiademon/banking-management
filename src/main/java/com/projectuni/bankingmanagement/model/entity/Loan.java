package com.projectuni.bankingmanagement.model.entity;

import com.projectuni.bankingmanagement.model.enums.LoanType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "customer_loan")
public final class Loan extends BaseEntity
{

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customers customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type")
    private LoanType loanType;

    @Column(name = "interest_rate")
    private int interestRate;

//    private LocalDateTime loanOpeningDate; createdAt in BaseEntity

    @Column(name = "the_principal_amount_of_the_loan")
    private long thePrincipalAmountOfTheLoan;

    @Column(name = "total_number_of_installments")
    private int totalNumberOfInstallments;

    @Column(name = "number_of_remaining_installments")
    private int numberOfRemainingInstallments;

    @Column(name = "amount_per_installment")
    private long amountPerInstallment;
}
