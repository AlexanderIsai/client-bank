package ua.danit.clientbank.controller;
import org.springframework.http.HttpStatus;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.save(account);
            return ResponseEntity.ok(createdAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestParam String number, @RequestParam Double amount) {
        Account account = accountService.deposit(number, amount);
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestParam String number, @RequestParam Double amount) {
        Account account = accountService.withdraw(number, amount);
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String fromNumber, @RequestParam String toNumber, @RequestParam Double amount) {
        boolean success = accountService.transfer(fromNumber, toNumber, amount);
        if (success) {
            return ResponseEntity.ok("Transfer successful");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transfer failed");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable long id) {
        boolean deleted = accountService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Account deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable long id) {
        Account account = accountService.getAccountById(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts);
    }
}