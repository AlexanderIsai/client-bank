package ua.danit.clientbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.danit.clientbank.model.Account;
import ua.danit.clientbank.model.Customer;
import ua.danit.clientbank.service.ir.AccountService;
import ua.danit.clientbank.service.ir.CustomerService;

import java.util.List;
import java.util.UUID;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCustomer(@PathVariable long id) {
        return customerService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable long id) {
        return customerService.getById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @PostMapping("/{customerId}/accounts")
    public ResponseEntity<?> createAccountForCustomer(@PathVariable Long customerId, @RequestBody Account account) {
        try {
            Customer customer = customerService.getById(customerId);
            if (customer != null) {
                account.setNumber(UUID.randomUUID().toString());
                account.setCustomer(customer);
                account.setBalance(account.getBalance() != null ? account.getBalance() : 0.0);
                accountService.save(account);
                customer.getAccounts().add(account);
                customerService.save(customer);
                return ResponseEntity.ok(account);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating account: " + e.getMessage());
        }
    }


    @DeleteMapping("/{customerId}/accounts/{accountId}")
    public boolean deleteAccountFromCustomer(@PathVariable long customerId, @PathVariable long accountId) {
        Customer customer = customerService.getById(customerId);
        if (customer != null) {
            Account accountToRemove = customer.getAccounts().stream()
                    .filter(account -> account.getId() == accountId)
                    .findFirst().orElse(null);

            if (accountToRemove != null) {
                customer.getAccounts().remove(accountToRemove);
                customerService.save(customer);
                accountService.delete(accountToRemove);
                return true;
            }
        }
        return false;
    }

}