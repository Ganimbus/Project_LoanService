package com.bbva.project_loanservice.controller;

import com.bbva.project_loanservice.dto.CustomerDTO;
import com.bbva.project_loanservice.entity.Customer;
import com.bbva.project_loanservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        Optional<CustomerDTO> customer = customerService.getCustomerById(customerId);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody List<CustomerDTO> customerDTOList) {
        // Convierte el DTO a la entidad antes de agregarlo
        for (CustomerDTO customerDTO : customerDTOList){
            customerService.addCustomer(customerDTO);
        }
        return new ResponseEntity<>("Customer added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO updatedCustomerDTO) {
        // Lógica para modificar un cliente por ID
        Customer updatedCustomer = convertDTOToEntity(updatedCustomerDTO);
        customerService.updateCustomer(customerId, updatedCustomer);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        // Lógica para eliminar un cliente por ID
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }

    // Método de conversión de DTO a entidad
    private Customer convertDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        // Configura los campos de la entidad según sea necesario
        customer.setDni(customerDTO.getDni());
        customer.setName(customerDTO.getName());
        customer.setLastname(customerDTO.getLastname());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setAddress(customerDTO.getAddress());
        customer.setRuc(customerDTO.getRuc());
        customer.setBusinessName(customerDTO.getBusinessName());
        customer.setCustomerType(customerDTO.getCustomerType());
        customer.setSalary(customerDTO.getSalary());

        return customer;
    }

    // Método de conversión de entidad a DTO
    private CustomerDTO convertEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        // Configura los campos del DTO según sea necesario
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setDni(customer.getDni());
        customerDTO.setName(customer.getName());
        customerDTO.setLastname(customer.getLastname());
        customerDTO.setTelephone(customer.getTelephone());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setRuc(customer.getRuc());
        customerDTO.setBusinessName(customer.getBusinessName());
        customerDTO.setCustomerType(customer.getCustomerType());
        customerDTO.setSalary(customer.getSalary());
        // Agrega otros campos según sea necesario
        return customerDTO;
    }
}
