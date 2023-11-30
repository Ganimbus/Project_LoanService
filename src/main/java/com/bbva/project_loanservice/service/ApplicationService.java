package com.bbva.project_loanservice.service;

import com.bbva.project_loanservice.dto.ApplicationDTO;
import com.bbva.project_loanservice.entity.Application;
import com.bbva.project_loanservice.entity.Customer;
import com.bbva.project_loanservice.entity.Employee;
import com.bbva.project_loanservice.repository.ApplicationRepository;
import com.bbva.project_loanservice.repository.BlacklistRepository;
import com.bbva.project_loanservice.repository.CustomerRepository;
import com.bbva.project_loanservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private BlacklistRepository blacklistRepository;

    /*@Autowired
    private LoanService loanService;*/

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

        return applicationDTO;
    }

    public void addApplication(ApplicationDTO applicationDTO) {
        // Obtiene el customerId del DTO
        Long customerId = applicationDTO.getCustomerId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer with ID " + customerId + " not found."));
        // Obtiene el DNI del cliente
        String dni = customer.getDni();
        // Verifica si el cliente está en la lista negra
        if (isCustomerInBlackList(dni)) {
            throw new RuntimeException("Customer with DNI " + dni + " is in the blacklist.");
        }
        // Verificar si el cliente existe
        if (!customerRepository.existsById(applicationDTO.getCustomerId())) {
            throw new RuntimeException("Customer with ID " + applicationDTO.getCustomerId() + " not found.");
        }
        // Verificar si el empleado existe
        if (!employeeRepository.existsById(applicationDTO.getEmployeeId())) {
            throw new RuntimeException("Employee with ID " + applicationDTO.getEmployeeId() + " not found.");
        }
        // Validar el monto mínimo del préstamo según el tipo de cliente
        //validateLoanAmount(applicationDTO);

        Application application = convertDTOToEntity(applicationDTO);

        // Añade la lógica para establecer el estado según las condiciones
        BigDecimal amount = application.getAmount();
        String customerType = application.getCustomer().getCustomerType();

        if ("negocio".equals(customerType) && amount.compareTo(new BigDecimal("30000.00")) < 0) {
            application.setState("rechazado");
        } else if ("persona".equals(customerType) && amount.compareTo(new BigDecimal("1000.00")) < 0) {
            application.setState("rechazado");
        } else {
            application.setState("aprobado");/*
            // Agrega el registro en la tabla LOAN cuando la solicitud es aprobada
            LoanDTO loanDTO = new LoanDTO();
            loanDTO.setCustomerId(application.getCustomer().getCustomerId());
            loanDTO.setApplicationId(application.getApplicationId());
            loanDTO.setAmount(application.getAmount());
            loanService.addLoan(loanDTO);*/
        }
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

    private boolean isCustomerInBlackList(String dni) {
        /*boolean isInBlackList = blacklistRepository.existsByDni(dni);
        System.out.println("DNI: " + dni + " isInBlackList: " + isInBlackList);*/
        return blacklistRepository.existsByDni(dni);
    }
    /*private void validateLoanAmount(ApplicationDTO applicationDTO) {
        Customer customer = customerRepository.findById(applicationDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found."));

        if ("Negocio".equals(customer.getCustomerType()) && applicationDTO.getAmount() < 30000.00) {
            throw new RuntimeException("El monto mínimo del préstamo para clientes tipo negocio es de 30000 soles.");
        } else if ("Persona".equals(customer.getCustomerType()) && applicationDTO.getAmount() < 1000.00) {
            throw new RuntimeException("El monto mínimo del préstamo para clientes tipo persona es de 1000 soles.");
        }
    }*/
}
