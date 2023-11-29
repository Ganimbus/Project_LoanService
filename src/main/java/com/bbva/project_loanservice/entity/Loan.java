package com.bbva.project_loanservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_ID")
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID")
    private Application application;

    @Column(name = "INSTALMENT")
    private int instalment;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "INTEREST_RATE")
    private BigDecimal interestRate;

    @Column(name = "INTEREST_TYPE")
    private String interestType;

    @Column(name = "FREQUENCY")
    private String frequency;

    @Column(name = "START_DATE")
    private Date startDate;

}
