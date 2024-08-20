package ua.danit.clientbank.controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerFacade customerFacade;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerFacade.createCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerFacade.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        customerFacade.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable long id) {
        CustomerResponse customerResponse = customerFacade.getCustomerById(id);
        if (customerResponse != null) {
            return ResponseEntity.ok(customerResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> customers = customerFacade.getAllCustomers(pageable);
        return ResponseEntity.ok(customers);
    }
}
