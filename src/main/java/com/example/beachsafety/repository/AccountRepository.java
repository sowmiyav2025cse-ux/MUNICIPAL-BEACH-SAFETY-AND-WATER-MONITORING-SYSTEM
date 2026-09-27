package com.example.beachsafety.repository;

import com.example.beachsafety.entity.Account;
import com.example.beachsafety.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountCode(String accountCode);
    Optional<Account> findByAccountName(String accountName);
    List<Account> findByAccountType(AccountType accountType);
}
