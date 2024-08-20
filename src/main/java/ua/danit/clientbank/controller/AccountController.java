package ua.danit.clientbank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.danit.clientbank.dto.account.AccountRequest;
import ua.danit.clientbank.dto.account.AccountResponse;
import ua.danit.clientbank.facade.AccountFacade;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountFacade accountFacade;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        AccountResponse createdAccount = accountFacade.createAccount(accountRequest);
        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id, @RequestBody AccountRequest accountRequest) {
        AccountResponse updatedAccount = accountFacade.updateAccount(id, accountRequest);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountFacade.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponse account = accountFacade.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> accounts = accountFacade.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
