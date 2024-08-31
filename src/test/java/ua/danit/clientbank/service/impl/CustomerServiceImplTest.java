package ua.danit.clientbank.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.danit.clientbank.model.Customer;
import ua.danit.clientbank.repository.AccountRepository;
import ua.danit.clientbank.repository.CustomerRepository;
import ua.danit.clientbank.repository.EmployerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setPassword("plaintext");
        when(passwordEncoder.encode("plaintext")).thenReturn("encoded");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.save(customer);
        assertNotNull(savedCustomer);
        assertEquals("encoded", savedCustomer.getPassword());
        verify(customerRepository).save(customer);
        verify(passwordEncoder).encode("plaintext");
    }

    @Test
    void testUpdateCustomer() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setPassword("oldpassword");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setPassword("newpassword");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNew");
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);
        assertNotNull(result);
        assertEquals("encodedNew", result.getPassword());
        verify(passwordEncoder).encode("newpassword");
        verify(customerRepository).save(existingCustomer);
    }

    @Test
    void testDeleteById() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);
        assertTrue(customerService.deleteById(1L));
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void testFindById() {
        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer foundCustomer = customerService.findById(1L);
        assertNotNull(foundCustomer);
        verify(customerRepository).findById(1L);
    }

    @Test
    void testFindByEmailNotFound() {
        when(customerRepository.findByEmail("test@example.com")).thenThrow(new UsernameNotFoundException("User not found"));
        assertThrows(UsernameNotFoundException.class, () -> customerService.findByEmail("test@example.com"));
    }

    @Test
    void testFindAll() {
        Pageable pageable = mock(Pageable.class);
        Page<Customer> page = mock(Page.class);
        when(customerRepository.findAll(pageable)).thenReturn(page);

        Page<Customer> result = customerService.findAll(pageable);
        assertNotNull(result);
        verify(customerRepository).findAll(pageable);
    }
}
