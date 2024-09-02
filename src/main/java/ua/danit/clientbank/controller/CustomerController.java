package ua.danit.clientbank.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.danit.clientbank.dto.customer.CustomerRequest;
import ua.danit.clientbank.dto.customer.CustomerResponse;
import ua.danit.clientbank.facade.CustomerFacade;

import java.util.List;

/**
 * CustomerController handles API requests for customer operations.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerFacade customerFacade;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        log.info("Creating customer with request data: {}", customerRequest);
        CustomerResponse customerResponse = customerFacade.createCustomer(customerRequest);
        log.info("Customer created successfully with ID: {}", customerResponse.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        log.info("Updating customer with ID: {} with request data: {}", id, customerRequest);
        CustomerResponse customerResponse = customerFacade.updateCustomer(id, customerRequest);
        log.info("Customer updated successfully with ID: {}", customerResponse.getId());
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        log.info("Deleting customer with ID: {}", id);
        customerFacade.deleteCustomer(id);
        log.info("Customer deleted successfully with ID: {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable long id) {
        log.info("Fetching customer details for ID: {}", id);
        CustomerResponse customerResponse = customerFacade.getCustomerById(id);
        if (customerResponse != null) {
            log.info("Customer details retrieved successfully for ID: {}", id);
            return ResponseEntity.ok(customerResponse);
        } else {
            log.info("No customer found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Fetching all customers with page: {} and size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> customers = customerFacade.getAllCustomers(pageable);
        log.info("Number of customers retrieved: {}", customers.getNumberOfElements());
        return ResponseEntity.ok(customers);
    }
}
