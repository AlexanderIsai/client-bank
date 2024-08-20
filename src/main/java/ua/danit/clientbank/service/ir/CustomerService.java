package ua.danit.clientbank.service.ir;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.danit.clientbank.model.Customer;
import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    Customer updateCustomer(Long id, Customer updatedCustomer);
    boolean deleteById(long id);
    Page<Customer> findAll(Pageable pageable);
    Customer findById(long id);
}
