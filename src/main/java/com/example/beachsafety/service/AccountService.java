package com.example.beachsafety.service;

import com.example.beachsafety.entity.Account;
import com.example.beachsafety.entity.AccountType;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByType(AccountType type) {
        return accountRepository.findByAccountType(type);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));
    }

    public Account createAccount(Account account) {
        if (account.getOpeningBalance() == null) {
            account.setOpeningBalance(0.0);
        }
        return accountRepository.save(account);
    }
}
