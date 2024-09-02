package ua.danit.clientbank.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ua.danit.clientbank.model.Account;
import ua.danit.clientbank.repository.AccountRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void testSave() {
        Account account = new Account();
        account.setId(1L);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        doNothing().when(messagingTemplate).convertAndSend(eq("/topic/accountChange"), any(Account.class));

        Account savedAccount = accountService.save(account);
        assertNotNull(savedAccount);
        assertEquals(1L, savedAccount.getId());
        verify(accountRepository).save(account);
        verify(messagingTemplate).convertAndSend(eq("/topic/accountChange"), any(Account.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(accountRepository).deleteById(anyLong());
        doNothing().when(messagingTemplate).convertAndSend(eq("/topic/accountDeleted"), anyLong());

        accountService.deleteById(1L);
        verify(accountRepository).deleteById(eq(1L));
        verify(messagingTemplate).convertAndSend(eq("/topic/accountDeleted"), eq(1L));
    }

    @Test
    void testFindAll() {
        when(accountRepository.findAll()).thenReturn(Arrays.asList(new Account()));
        List<Account> accounts = accountService.findAll();
        assertFalse(accounts.isEmpty());
        assertEquals(1, accounts.size());
        verify(accountRepository).findAll();
    }

    @Test
    void testFindById() {
        Account account = new Account();
        account.setId(1L);
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        Account foundAccount = accountService.findById(1L);
        assertNotNull(foundAccount);
        assertEquals(1L, foundAccount.getId());
        verify(accountRepository).findById(eq(1L));
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
        when(accountRepository.findByNumber(eq("fromAccount"))).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByNumber(eq("toAccount"))).thenReturn(Optional.of(toAccount));
        doNothing().when(messagingTemplate).convertAndSend(anyString(), any(Account.class));

        boolean result = accountService.transfer("fromAccount", "toAccount", 50.0);
        assertTrue(result);
        assertEquals(150.0, fromAccount.getBalance());
        assertEquals(150.0, toAccount.getBalance());
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(messagingTemplate, times(2)).convertAndSend(eq("/topic/accountChange"), any(Account.class));
    }

    @Test
    void testUpdate() {
        Account account = new Account();
        account.setId(1L);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        doNothing().when(messagingTemplate).convertAndSend(anyString(), any(Account.class));

        Account updatedAccount = accountService.update(account);
        assertNotNull(updatedAccount);
        assertEquals(1L, updatedAccount.getId());
        verify(accountRepository).save(account);
        verify(messagingTemplate).convertAndSend(eq("/topic/accountChange"), any(Account.class));
    }

    @Test
    void testFindByAccountNumberNotFound() {
        when(accountRepository.findByNumber(anyString())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> accountService.findByAccountNumber("nonexistent"));
    }
}
