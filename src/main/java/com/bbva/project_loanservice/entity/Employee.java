package com.bbva.project_loanservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "DNI")
    private String dni;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "POSITION")
    private String position;
}