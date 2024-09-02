package ua.danit.clientbank.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ua.danit.clientbank.dto.account.AccountRequest;
import ua.danit.clientbank.dto.account.AccountResponse;
import ua.danit.clientbank.facade.AccountFacade;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountFacade accountFacade;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        log.info("Attempting to create account with details: {}", accountRequest);
        AccountResponse createdAccount = accountFacade.createAccount(accountRequest);
        log.info("Account created successfully with ID: {}", createdAccount.getId());

        messagingTemplate.convertAndSend("/topic/accountChange", createdAccount);

        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id, @RequestBody AccountRequest accountRequest) {
        log.info("Attempting to update account with ID: {} with new details: {}", id, accountRequest);
        AccountResponse updatedAccount = accountFacade.updateAccount(id, accountRequest);
        log.info("Account updated successfully with ID: {}", updatedAccount.getId());

        messagingTemplate.convertAndSend("/topic/accountChange", updatedAccount);

        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        log.info("Attempting to delete account with ID: {}", id);
        accountFacade.deleteAccount(id);
        log.info("Account deleted successfully with ID: {}", id);

        messagingTemplate.convertAndSend("/topic/accountChange", "Account ID " + id + " deleted");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        log.info("Fetching account details for ID: {}", id);
        AccountResponse account = accountFacade.getAccountById(id);
        if (account == null) {
            log.info("No account found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } else {
            log.info("Account details retrieved successfully for ID: {}", id);
            return ResponseEntity.ok(account);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        log.info("Fetching all accounts");
        List<AccountResponse> accounts = accountFacade.getAllAccounts();
        log.info("Number of accounts retrieved: {}", accounts.size());

        return ResponseEntity.ok(accounts);
    }
}
