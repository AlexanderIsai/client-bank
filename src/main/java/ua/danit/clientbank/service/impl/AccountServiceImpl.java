package ua.danit.clientbank.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.danit.clientbank.model.Account;
import ua.danit.clientbank.repository.AccountRepository;
import ua.danit.clientbank.service.ir.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Account save(Account account) {
        Account savedAccount = accountRepository.save(account);
        messagingTemplate.convertAndSend("/topic/accountChange", savedAccount);
        return savedAccount;
    }

    @Override
    public void deleteById(long id) {
        accountRepository.deleteById(id);
        messagingTemplate.convertAndSend("/topic/accountDeleted", id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account deposit(String number, Double amount) {
        return accountRepository.findByNumber(number)
                .map(account -> {
                    if (amount > 0) {
                        account.setBalance(account.getBalance() + amount);
                        Account updatedAccount = accountRepository.save(account);
                        messagingTemplate.convertAndSend("/topic/accountChange", updatedAccount);
                        return updatedAccount;
                    }
                    return null;
                }).orElse(null);
    }

    @Override
    public Account withdraw(String number, Double amount) {
        return accountRepository.findByNumber(number)
                .filter(account -> account.getBalance() >= amount)
                .map(account -> {
                    account.setBalance(account.getBalance() - amount);
                    Account updatedAccount = accountRepository.save(account);
                    messagingTemplate.convertAndSend("/topic/accountChange", updatedAccount);
                    return updatedAccount;
                }).orElse(null);
    }

    @Override
    public boolean transfer(String fromNumber, String toNumber, Double amount) {
        Optional<Account> fromAccount = accountRepository.findByNumber(fromNumber);
        Optional<Account> toAccount = accountRepository.findByNumber(toNumber);

        if (fromAccount.isPresent() && toAccount.isPresent() && amount > 0 && fromAccount.get().getBalance() >= amount) {
            Account from = fromAccount.get();
            Account to = toAccount.get();
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            accountRepository.save(from);
            accountRepository.save(to);
            messagingTemplate.convertAndSend("/topic/accountChange", from);
            messagingTemplate.convertAndSend("/topic/accountChange", to);
            return true;
        }
        return false;
    }

    @Override
    public Account update(Account account) {
        Account updatedAccount = accountRepository.save(account);
        messagingTemplate.convertAndSend("/topic/accountChange", updatedAccount);
        return updatedAccount;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber).orElseThrow();
    }
}
