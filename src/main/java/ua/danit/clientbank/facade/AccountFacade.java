package ua.danit.clientbank.facade;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.danit.clientbank.dto.account.AccountRequest;
import ua.danit.clientbank.dto.account.AccountResponse;
import ua.danit.clientbank.model.Account;
import ua.danit.clientbank.service.ir.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountFacade {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountFacade(AccountService accountService, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    public AccountResponse createAccount(AccountRequest request) {
        Account account = modelMapper.map(request, Account.class);
        account = accountService.save(account);
        return modelMapper.map(account, AccountResponse.class);
    }

    public AccountResponse updateAccount(Long id, AccountRequest request) {
        Account existingAccount = accountService.findById(id);
        modelMapper.map(request, existingAccount);
        Account updatedAccount = accountService.save(existingAccount);
        return modelMapper.map(updatedAccount, AccountResponse.class);
    }

    public AccountResponse getAccountById(Long id) {
        Account account = accountService.findById(id);
        return modelMapper.map(account, AccountResponse.class);
    }

    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountService.findAll();
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }

    public void deleteAccount(Long id) {
        accountService.deleteById(id);
    }
}
