package com.projectuni.bankingmanagement.model.entity;

import com.projectuni.bankingmanagement.model.enums.CustomerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "customer")
public final class Customer extends BaseEntity
{
    @Column(name = "customer_name", nullable = false, length = 30)
    private String name;

    @Column(name = "customer_family", nullable = false, length = 30)
    private String family;

    @Column(name = "national_code", nullable = false)
    private int nationalCode;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "customer_status", nullable = false)
    private boolean status = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType type;

    @Column(name = "customer_phone_number")
    private String phoneNumber;
}
