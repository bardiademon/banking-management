package com.projectuni.bankingmanagement.model.entity;

import com.projectuni.bankingmanagement.model.enums.TransactionsStatus;
import com.projectuni.bankingmanagement.model.enums.TransactionsType;
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
@Table(name = "transactions")
public final class Transactions extends BaseEntity
{
    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "transactions_status")
    private TransactionsStatus transactionsStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "transactions_type")
    private TransactionsType transactionsType;

    @ManyToOne
    @JoinColumn(name = "to_number", referencedColumnName = "id")
    private Deposit to;

    @ManyToOne
    @JoinColumn(name = "from_number", referencedColumnName = "id")
    private Deposit from;

    @Column(name = "issue_tracking", nullable = false)
    private int issueTracking;

    @Column(name = "transactions_description", length = 900)
    private String description;
}
