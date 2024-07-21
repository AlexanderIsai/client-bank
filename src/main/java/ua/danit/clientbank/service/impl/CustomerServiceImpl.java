package ua.danit.clientbank.service.impl;
import ua.danit.clientbank.dao.CustomerDAO;
import ua.danit.clientbank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.danit.clientbank.service.ir.CustomerService;
/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */


import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerDao.getOne(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerDao.getOne(id);
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setAge(customer.getAge());
            return customerDao.save(existingCustomer);
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(Long id) {
        return customerDao.deleteById(id);
    }
}
