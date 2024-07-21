package ua.danit.clientbank.service.ir;

import ua.danit.clientbank.model.Account;

import java.util.List;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */
public interface AccountService {
    Account createAccount(Account account);
    Account getAccountById(Long id);
    List<Account> getAllAccounts();
    Account updateAccount(Long id, Account account);
    boolean deleteAccount(Long id);
    Account findByAccountNumber(String accountNumber);
}
