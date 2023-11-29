package com.bbva.project_loanservice.dto;

import java.math.BigDecimal;
import java.util.Date;


public class LoanDTO {
    private Long loanId;
    private Long customerId;
    private Long applicationId;
    private int instalment;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private String interestType;
    private String frequency;
    private Date startDate;
}