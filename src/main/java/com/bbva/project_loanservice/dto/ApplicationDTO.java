package com.bbva.project_loanservice.dto;

import java.math.BigDecimal;
import java.util.Date;


public class ApplicationDTO {
    private Long applicationId;
    private Long employeeId;
    private Long customerId;
    private BigDecimal amount;
    private String state;
    private Date createDate;
}