package com.bbva.project_loanservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "black_list")
public class BlackList {
    @Id
    @Column(name = "DNI")
    private String dni;

    @Column(name = "REASON")
    private String reason;
}
