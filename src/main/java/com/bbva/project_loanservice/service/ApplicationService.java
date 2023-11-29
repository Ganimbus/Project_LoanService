package com.bbva.project_loanservice.service;

import com.bbva.project_loanservice.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    // Métodos para la lógica de negocio relacionada con Application
}
