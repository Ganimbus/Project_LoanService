package com.bbva.project_loanservice.controller;

import com.bbva.project_loanservice.dto.LoanDTO;
import com.bbva.project_loanservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/all")
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> allLoans = loanService.getAllLoans();
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLoan(@RequestBody List<LoanDTO> loanDTOList) {
        for (LoanDTO loanDTO : loanDTOList) {
            loanService.addLoan(loanDTO);
        }
        return new ResponseEntity<>("Loan added successfully", HttpStatus.CREATED);
    }
}
