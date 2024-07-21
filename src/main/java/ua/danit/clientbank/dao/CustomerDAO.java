package ua.danit.clientbank.dao;
import ua.danit.clientbank.model.Customer;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */


@Repository
public class CustomerDAO implements Dao<Customer> {
    private final List<Customer> customers = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public Customer save(Customer customer) {
        customer.setId(idCounter.incrementAndGet());
        customers.add(customer);
        return customer;
    }

    @Override
    public boolean delete(Customer customer) {
        return customers.remove(customer);
    }

    @Override
    public void deleteAll(List<Customer> entities) {
        customers.removeAll(entities);
    }

    @Override
    public void saveAll(List<Customer> entities) {
        entities.forEach(this::save);
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    @Override
    public boolean deleteById(long id) {
        return customers.removeIf(customer -> customer.getId() == id);
    }

    @Override
    public Customer getOne(long id) {
        return customers.stream().filter(customer -> customer.getId() == id).findFirst().orElse(null);
    }
}
