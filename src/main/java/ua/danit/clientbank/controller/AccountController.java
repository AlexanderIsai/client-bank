package ua.danit.clientbank.controller;
import org.springframework.http.ResponseEntity;
import ua.danit.clientbank.model.Account;
import ua.danit.clientbank.service.ir.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccount(id);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestParam String accountNumber, @RequestParam Double amount) {
        Account account = accountService.findByAccountNumber(accountNumber);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        account.setBalance(account.getBalance() + amount);
        accountService.updateAccount(account.getId(), account);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestParam String accountNumber, @RequestParam Double amount) {
        Account account = accountService.findByAccountNumber(accountNumber);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        if (account.getBalance() < amount) {
            return ResponseEntity.badRequest().body(null);
        }
        account.setBalance(account.getBalance() - amount);
        accountService.updateAccount(account.getId(), account);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Account> transfer(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestParam Double amount) {
        Account fromAccount = accountService.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountService.findByAccountNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            return ResponseEntity.notFound().build();
        }
        if (fromAccount.getBalance() < amount) {
            return ResponseEntity.badRequest().body(null);
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountService.updateAccount(fromAccount.getId(), fromAccount);
        accountService.updateAccount(toAccount.getId(), toAccount);

        return ResponseEntity.ok(fromAccount);
    }
}
