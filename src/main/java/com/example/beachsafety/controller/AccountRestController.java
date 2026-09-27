package com.example.beachsafety.controller;

import com.example.beachsafety.entity.Account;
import com.example.beachsafety.entity.AccountType;
import com.example.beachsafety.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(required = false) AccountType type) {
        if (type != null) {
            return ResponseEntity.ok(accountService.getAccountsByType(type));
        }
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account created = accountService.createAccount(account);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
