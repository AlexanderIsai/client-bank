package ua.danit.clientbank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.danit.clientbank.model.Customer;
import ua.danit.clientbank.repository.AccountRepository;
import ua.danit.clientbank.repository.CustomerRepository;
import ua.danit.clientbank.repository.EmployerRepository;
import ua.danit.clientbank.service.ir.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final EmployerRepository employerRepository;

    @Autowired
    public CustomerServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, EmployerRepository employerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.employerRepository = employerRepository;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        accountRepository.saveAll(customer.getAccounts());
        employerRepository.saveAll(customer.getEmployers());
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setAge(updatedCustomer.getAge());

        existingCustomer.getAccounts().forEach(account -> account.setCustomer(existingCustomer));
        accountRepository.saveAll(existingCustomer.getAccounts());

        existingCustomer.getEmployers().clear();
        existingCustomer.getEmployers().addAll(updatedCustomer.getEmployers());
        employerRepository.saveAll(existingCustomer.getEmployers());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public boolean deleteById(long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }


    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
