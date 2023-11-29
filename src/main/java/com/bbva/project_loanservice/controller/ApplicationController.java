package com.bbva.project_loanservice.controller;

import com.bbva.project_loanservice.dto.ApplicationDTO;
import com.bbva.project_loanservice.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        List<ApplicationDTO> allApplications = applicationService.getAllApplications();
        return new ResponseEntity<>(allApplications, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addApplication(@RequestBody List<ApplicationDTO> applicationDTOList) {
        for (ApplicationDTO applicationDTO : applicationDTOList){
            applicationService.addApplication(applicationDTO);
        }
        return new ResponseEntity<>("Application added successfully", HttpStatus.CREATED);
    }
}