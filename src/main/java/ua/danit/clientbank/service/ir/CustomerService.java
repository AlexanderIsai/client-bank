package ua.danit.clientbank.service.ir;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */
import ua.danit.clientbank.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Long id, Customer customer);
    boolean deleteCustomer(Long id);
}
