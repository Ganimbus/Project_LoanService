package com.bbva.project_loanservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ApplicationDTO {
    private Long applicationId;
    private Long employeeId;
    private Long customerId;
    private BigDecimal amount;
    private String state;
    private Date createDate;
}