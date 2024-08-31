package ua.danit.clientbank.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.danit.clientbank.model.Account;
import ua.danit.clientbank.repository.AccountRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void testSave() {
        Account account = new Account();
        account.setId(1L);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account savedAccount = accountService.save(account);
        assertNotNull(savedAccount);
        verify(accountRepository).save(account);
    }

    @Test
    void testDeleteById() {
        doNothing().when(accountRepository).deleteById(1L);
        accountService.deleteById(1L);
        verify(accountRepository).deleteById(1L);
    }

    @Test
    void testFindAll() {
        Account account = new Account();
        when(accountRepository.findAll()).thenReturn(Arrays.asList(account));

        List<Account> accounts = accountService.findAll();
        assertFalse(accounts.isEmpty());
        verify(accountRepository).findAll();
    }

    @Test
    void testFindById() {
        Account account = new Account();
        account.setId(1L);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.findById(1L);
        assertNotNull(foundAccount);
        verify(accountRepository).findById(1L);
    }

    @Test
    void testDeposit() {
        Account account = new Account();
        account.setBalance(100.0);
        when(accountRepository.findByNumber("123456")).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.deposit("123456", 50.0);
        assertNotNull(updatedAccount);
        assertEquals(150.0, updatedAccount.getBalance());
    }

    @Test
    void testWithdraw() {
        Account account = new Account();
        account.setBalance(200.0);
        when(accountRepository.findByNumber("123456")).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.withdraw("123456", 50.0);
        assertNotNull(updatedAccount);
        assertEquals(150.0, updatedAccount.getBalance());
    }

    @Test
    void testTransfer() {
        Account fromAccount = new Account();
        fromAccount.setBalance(200.0);
        Account toAccount = new Account();
        toAccount.setBalance(100.0);
        when(accountRepository.findByNumber("fromAccount")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByNumber("toAccount")).thenReturn(Optional.of(toAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        boolean result = accountService.transfer("fromAccount", "toAccount", 50.0);
        assertTrue(result);
        assertEquals(150.0, fromAccount.getBalance());
        assertEquals(150.0, toAccount.getBalance());
    }

    @Test
    void testUpdate() {
        Account account = new Account();
        account.setId(1L);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.update(account);
        assertNotNull(updatedAccount);
        verify(accountRepository).save(account);
    }

    @Test
    void testFindByAccountNumberNotFound() {
        when(accountRepository.findByNumber("nonexistent")).thenThrow(new RuntimeException("Account not found"));
        assertThrows(RuntimeException.class, () -> accountService.findByAccountNumber("nonexistent"));
    }
}
