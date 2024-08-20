package ua.danit.clientbank.service.ir;

import ua.danit.clientbank.model.Account;
import java.util.List;

public interface AccountService {
    Account save(Account account);
    void deleteById(long id);
    List<Account> findAll();
    Account findById(long id);
    Account deposit(String number, Double amount);
    Account withdraw(String number, Double amount);
    boolean transfer(String fromNumber, String toNumber, Double amount);
    Account update(Account account);
    Account findByAccountNumber(String accountNumber);
}
