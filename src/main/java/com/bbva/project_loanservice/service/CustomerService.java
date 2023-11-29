package com.bbva.project_loanservice.service;

import com.bbva.project_loanservice.dto.CustomerDTO;
import com.bbva.project_loanservice.entity.Customer;
import com.bbva.project_loanservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> getCustomerById(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        return customerOptional.map(this::convertEntityToDTO);
    }

    public void addCustomer(CustomerDTO customerDTO) {
        Customer customer = convertDTOToEntity(customerDTO);
        customerRepository.save(customer);
    }

    public void updateCustomer(Long customerId, Customer updatedCustomerDTO) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(customerId);
        existingCustomerOptional.ifPresent(existingCustomer -> {
            // Actualiza los campos necesarios
            existingCustomer.setDni(updatedCustomerDTO.getDni());
            existingCustomer.setName(updatedCustomerDTO.getName());
            existingCustomer.setLastname(updatedCustomerDTO.getLastname());
            existingCustomer.setTelephone(updatedCustomerDTO.getTelephone());
            existingCustomer.setAddress(updatedCustomerDTO.getAddress());
            existingCustomer.setRuc(updatedCustomerDTO.getRuc());
            existingCustomer.setBusinessName(updatedCustomerDTO.getBusinessName());
            existingCustomer.setCustomerType(updatedCustomerDTO.getCustomerType());
            existingCustomer.setSalary(updatedCustomerDTO.getSalary());

            customerRepository.save(existingCustomer);
        });
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    private CustomerDTO convertEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
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

    private Customer convertDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setDni(customerDTO.getDni());
        customer.setName(customerDTO.getName());
        customer.setLastname(customerDTO.getLastname());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setAddress(customerDTO.getAddress());
        customer.setRuc(customerDTO.getRuc());
        customer.setBusinessName(customerDTO.getBusinessName());
        customer.setCustomerType(customerDTO.getCustomerType());
        customer.setSalary(customerDTO.getSalary());
        // Agrega otros campos según sea necesario
        return customer;
    }
}