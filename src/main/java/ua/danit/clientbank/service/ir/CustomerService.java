package ua.danit.clientbank.service.ir;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */
import ua.danit.clientbank.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    Customer getById(long id);
    List<Customer> findAll();
    Customer updateCustomer(Long id, Customer updatedCustomer);
    boolean delete(Customer customer);
    void deleteAll(List<Customer> customers);
    boolean deleteById(long id);
}
