package ua.danit.clientbank.dao;
import ua.danit.clientbank.model.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */


@Repository
public class AccountDAO implements Dao<Account> {
    private final List<Account> accounts = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public Account save(Account account) {
        account.setId(idCounter.incrementAndGet());
        accounts.add(account);
        return account;
    }

    @Override
    public boolean delete(Account account) {
        return accounts.remove(account);
    }

    @Override
    public void deleteAll(List<Account> entities) {
        accounts.removeAll(entities);
    }

    @Override
    public void saveAll(List<Account> entities) {
        entities.forEach(this::save);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    @Override
    public boolean deleteById(long id) {
        return accounts.removeIf(account -> account.getId() == id);
    }

    @Override
    public Account getOne(long id) {
        return accounts.stream().filter(account -> account.getId() == id).findFirst().orElse(null);
    }

    public Account findByAccountNumber(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
}
