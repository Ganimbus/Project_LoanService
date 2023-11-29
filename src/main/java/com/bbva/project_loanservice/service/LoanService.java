package com.bbva.project_loanservice.service;

import com.bbva.project_loanservice.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    // Métodos para la lógica de negocio relacionada con Loan
}
