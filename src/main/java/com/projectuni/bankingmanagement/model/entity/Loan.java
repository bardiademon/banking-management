package com.projectuni.bankingmanagement.model.entity;

import com.projectuni.bankingmanagement.model.enums.LoanStatus;
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
@Table(name = "deposit_loan")
public final class Loan extends BaseEntity
{
    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type")
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status")
    private LoanStatus loanStatus;

    @Column(name = "interest_rate")
    private int interestRate;

    @ManyToOne
    @JoinColumn(name = "deposit_id", referencedColumnName = "id")
    private Deposit deposit;

//    private LocalDateTime loanOpeningDate; createdAt in BaseEntity

    // مبلغ اصلی وام
    @Column(name = "the_principal_amount_of_the_loan")
    private double thePrincipalAmountOfTheLoan;

    // تعداد کل اقساط
    @Column(name = "total_number_of_installments")
    private int totalNumberOfInstallments;

    // تعداد اقساط باقیمانده
    @Column(name = "number_of_remaining_installments")
    private int numberOfRemainingInstallments;

    // مبلغ در هر قسط
    @Column(name = "amount_per_installment")
    private double amountPerInstallment;
}
