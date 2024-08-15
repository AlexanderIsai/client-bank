package ua.danit.clientbank.service.impl;
import jakarta.transaction.Transactional;
import ua.danit.clientbank.model.Account;
import ua.danit.clientbank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.danit.clientbank.repository.AccountRepository;
import ua.danit.clientbank.repository.CustomerRepository;
import ua.danit.clientbank.repository.EmployerRepository;
import ua.danit.clientbank.service.ir.CustomerService;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployerRepository employerRepository;

    public Customer save(Customer customer) {
        accountRepository.saveAll(customer.getAccounts());
        customer.getEmployers().forEach(employer -> {
            System.out.println("Saving employer: " + employer);
            employerRepository.save(employer);
        });
        System.out.println("Saving customer: " + customer);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        if (!customerRepository.existsById(id)) {
            return null;
        }

        Customer existingCustomer = customerRepository.findById(id).orElseThrow();
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setAge(updatedCustomer.getAge());

        existingCustomer.getAccounts().clear();
        existingCustomer.getAccounts().addAll(updatedCustomer.getAccounts());
        existingCustomer.getAccounts().forEach(account -> account.setCustomer(existingCustomer));
        accountRepository.saveAll(existingCustomer.getAccounts());

        existingCustomer.getEmployers().clear();
        existingCustomer.getEmployers().addAll(updatedCustomer.getEmployers());
        employerRepository.saveAll(existingCustomer.getEmployers());

        return customerRepository.save(existingCustomer);
    }

    public boolean delete(Customer customer) {
        if (customerRepository.existsById(customer.getId())) {
            customerRepository.delete(customer);
            return true;
        }
        return false;
    }

    public void deleteAll(List<Customer> customers) {
        customerRepository.deleteAll(customers);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public boolean deleteById(long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Customer getById(long id) {
        return customerRepository.findById(id).orElse(null);
    }
}