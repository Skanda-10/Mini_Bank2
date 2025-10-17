package com.ash.Account_Service.controller;

import com.ash.Account_Service.entity.Account;
import com.ash.Account_Service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/user/{userId}")
    public Account createAccount(@PathVariable Long userId, @RequestBody Account account) {
        return accountService.createAccount(account, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        if (account != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", account.getId());
            response.put("userId", account.getUser().getId());
            response.put("userName", account.getUser().getName());
            response.put("balance", account.getBalance());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public List<Account> getAccountsByUserId(@PathVariable Long userId) {
        return accountService.getAccountsByUserId(userId);
    }

    // This new endpoint handles all transaction types (deposit/withdraw) by
    // delegating to the AccountService's processTransaction method.
    @PostMapping("/{accountId}/transact")
    public ResponseEntity<Account> handleTransaction(@PathVariable Long accountId,
                                                     @RequestParam Double amount,
                                                     @RequestParam String type) {
        try {
            Account updatedAccount = accountService.processTransaction(accountId, amount, type);
            return ResponseEntity.ok(updatedAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}