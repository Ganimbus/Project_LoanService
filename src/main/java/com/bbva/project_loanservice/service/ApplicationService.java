package com.bbva.project_loanservice.service;

import com.bbva.project_loanservice.dto.ApplicationDTO;
import com.bbva.project_loanservice.entity.Application;
import com.bbva.project_loanservice.entity.Customer;
import com.bbva.project_loanservice.entity.Employee;
import com.bbva.project_loanservice.repository.ApplicationRepository;
import com.bbva.project_loanservice.repository.CustomerRepository;
import com.bbva.project_loanservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CustomerRepository customerRepository; // Repositorio de Customer inyectado

    @Autowired
    private EmployeeRepository employeeRepository; // Repositorio de Employee inyectado

    public List<ApplicationDTO> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private ApplicationDTO convertEntityToDTO(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setApplicationId(application.getApplicationId());
        applicationDTO.setEmployeeId(application.getEmployee().getEmployeeId());
        applicationDTO.setCustomerId(application.getCustomer().getCustomerId());
        applicationDTO.setAmount(application.getAmount());
        applicationDTO.setState(application.getState());
        applicationDTO.setCreateDate(application.getCreateDate());

        // Agrega otros campos según sea necesario
        return applicationDTO;
    }

    public void addApplication(ApplicationDTO applicationDTO) {
        // Verificar si el cliente existe
        if (!customerRepository.existsById(applicationDTO.getCustomerId())) {
            throw new RuntimeException("Customer with ID " + applicationDTO.getCustomerId() + " not found.");
        }
        // Verificar si el empleado existe
        if (!employeeRepository.existsById(applicationDTO.getEmployeeId())) {
            throw new RuntimeException("Employee with ID " + applicationDTO.getEmployeeId() + " not found.");
        }
        Application application = convertDTOToEntity(applicationDTO);
        applicationRepository.save(application);
    }

    private Application convertDTOToEntity(ApplicationDTO applicationDTO) {
        Application application = new Application();
        application.setApplicationId(applicationDTO.getApplicationId());

        // Asignar el empleado con base en el ID proporcionado en el DTO
        Employee employee = new Employee();
        employee.setEmployeeId(applicationDTO.getEmployeeId());
        application.setEmployee(employee);

        // Asignar el cliente con base en el ID proporcionado en el DTO
        Customer customer = new Customer();
        customer.setCustomerId(applicationDTO.getCustomerId());
        application.setCustomer(customer);

        application.setAmount(applicationDTO.getAmount());
        application.setState(applicationDTO.getState());
        application.setCreateDate(applicationDTO.getCreateDate());

        return application;
    }
}
