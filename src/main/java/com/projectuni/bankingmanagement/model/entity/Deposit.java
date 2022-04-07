package com.projectuni.bankingmanagement.model.entity;

import com.projectuni.bankingmanagement.model.enums.DepositCurrency;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.enums.DepositType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "deposit")
public final class Deposit extends BaseEntity
{
    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_type", nullable = false)
    private DepositType depositType;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Customers> customers;

    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_status", nullable = false)
    private DepositStatus depositStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_currency")
    private DepositCurrency depositCurrency;

    @Column(name = "account_inventory")
    private double accountInventory = 0;

    @Column(name = "validity_start_date", nullable = false)
    private LocalDateTime validityStartDate;

    @Column(name = "credit_expiration_date")
    private LocalDateTime creditExpirationDate;
}