package ua.danit.clientbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.danit.clientbank.model.Account;

import java.util.Optional;

/**
 * description
 *
 * @author Alexander Isai on 30.07.2024.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String number);

}
