package com.bbva.project_loanservice.service;

import com.bbva.project_loanservice.dto.LoanDTO;
import com.bbva.project_loanservice.entity.Application;
import com.bbva.project_loanservice.entity.Loan;
import com.bbva.project_loanservice.repository.ApplicationRepository;
import com.bbva.project_loanservice.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public void addLoan(LoanDTO loanDTO) {
    // Obtener la solicitud asociada al ID proporcionado en el DTO
        Optional<Application> applicationOptional = applicationRepository.findById(loanDTO.getApplicationId());
        if (applicationOptional.isPresent()) {
            Application application = applicationOptional.get();

            // Verificar si la solicitud está aprobada
            if ("aprobado".equals(application.getState())) {
                // Rellenar campos de Loan con los datos de la solicitud
                Loan loan = new Loan();
                loan.setCustomer(application.getCustomer());
                loan.setApplication(application);
                loan.setInstalment(loanDTO.getInstalment());
                loan.setAmount(application.getAmount());
                loan.setInterestRate(loanDTO.getInterestRate());
                loan.setInterestType(loanDTO.getInterestType());
                loan.setFrequency(loanDTO.getFrequency());
                loan.setStartDate(loanDTO.getStartDate());

                // Guardar el préstamo en la base de datos
                loanRepository.save(loan);
            } else {
                throw new RuntimeException("Cannot add a Loan for an Application with state other than 'approved'.");
            }
        } else {
            throw new RuntimeException("Application not found for the provided ID: " + loanDTO.getApplicationId());
        }
    }

    public List<LoanDTO> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }
    private LoanDTO convertEntityToDTO(Loan loan) {
           LoanDTO loanDTO = new LoanDTO();
           loanDTO.setLoanId(loan.getLoanId());
           loanDTO.setCustomerId(loan.getCustomer().getCustomerId());
           loanDTO.setApplicationId(loan.getApplication().getApplicationId());
           loanDTO.setInstalment(loan.getInstalment());
           loanDTO.setAmount(loan.getAmount());
           loanDTO.setInterestRate(loan.getInterestRate());
           loanDTO.setInterestType(loan.getInterestType());
           loanDTO.setFrequency(loan.getFrequency());
           loanDTO.setStartDate(loan.getStartDate());

           return loanDTO;
    }

}