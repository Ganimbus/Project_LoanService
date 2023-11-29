package com.bbva.project_loanservice.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CustomerDTO {
    private Long customerId;
    private String dni;
    private String name;
    private String lastname;
    private String telephone;
    private String address;
    private String ruc;
    private String businessName;
    private String customerType;
    private BigDecimal salary;
}