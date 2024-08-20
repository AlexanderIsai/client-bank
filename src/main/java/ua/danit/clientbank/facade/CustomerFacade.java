package ua.danit.clientbank.facade;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ua.danit.clientbank.dto.customer.CustomerRequest;
import ua.danit.clientbank.dto.customer.CustomerResponse;
import ua.danit.clientbank.model.Customer;
import ua.danit.clientbank.service.ir.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerFacade {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerFacade(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = modelMapper.map(request, Customer.class);
        customer = customerService.save(customer);
        return modelMapper.map(customer, CustomerResponse.class);
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer existingCustomer = customerService.findById(id);
        if (existingCustomer == null) {
            throw new RuntimeException("Customer not found");
        }
        modelMapper.map(request, existingCustomer);
        Customer updatedCustomer = customerService.updateCustomer(id, existingCustomer);
        return modelMapper.map(updatedCustomer, CustomerResponse.class);
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        return modelMapper.map(customer, CustomerResponse.class);
    }

    public void deleteCustomer(Long id) {
        if (!customerService.deleteById(id)) {
            throw new RuntimeException("Failed to delete customer or customer not found");
        }
    }

    public Page<CustomerResponse> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerService.findAll(pageable);
        return customers.map(customer -> modelMapper.map(customer, CustomerResponse.class));
    }


}
