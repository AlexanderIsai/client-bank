package ua.danit.clientbank.service.impl;
import ua.danit.clientbank.dao.AccountDAO;
import ua.danit.clientbank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.danit.clientbank.service.ir.AccountService;

import java.util.List;
/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */


@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDao;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account createAccount(Account account) {
        return accountDao.save(account);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountDao.getOne(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.findAll();
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        Account existingAccount = accountDao.getOne(id);
        if (existingAccount != null) {
            existingAccount.setCurrency(account.getCurrency());
            existingAccount.setBalance(account.getBalance());
            existingAccount.setCustomer(account.getCustomer());
            return accountDao.save(existingAccount);
        }
        return null;
    }

    @Override
    public boolean deleteAccount(Long id) {
        return accountDao.deleteById(id);
    }

        @Override
        public Account findByAccountNumber(String accountNumber) {
            return accountDao.findByAccountNumber(accountNumber);
        }
}
