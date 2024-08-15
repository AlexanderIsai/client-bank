package ua.danit.clientbank.service.ir;

import ua.danit.clientbank.model.Account;

import java.util.List;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */
public interface AccountService {
    Account save(Account account);
    boolean delete(Account account);
    void deleteAll(List<Account> accounts);
    List<Account> findAll();
    boolean deleteById(long id);
    Account getAccountById(long id);
    List<Account> getAllAccounts();
    Account deposit(String number, Double amount);
    Account withdraw(String number, Double amount);
    boolean transfer(String fromNumber, String toNumber, Double amount);
    Account updateAccount(long id, Account account);

    Account findByAccountNumber(String accountNumber);



}
