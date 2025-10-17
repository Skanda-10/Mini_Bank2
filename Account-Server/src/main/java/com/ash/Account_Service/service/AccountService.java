package com.ash.Account_Service.service;

import com.ash.Account_Service.DTO.TransactionRequestDTO;
import com.ash.Account_Service.entity.Account;
import com.ash.Account_Service.entity.User;
import com.ash.Account_Service.repository.AccountRepository;
import com.ash.Account_Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String transactionServiceUrl = "http://TRANSACTION-SERVICE/transactions/process";

    public Account createAccount(Account account, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            account.setUser(user);
            return accountRepository.save(account);
        }
        return null;
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAccountsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? accountRepository.findByUser(user) : null;
    }

    public Account processTransaction(Long accountId, Double amount, String type) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        User user = account.getUser();

        if (type.equalsIgnoreCase("withdraw") && account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        TransactionRequestDTO request = new TransactionRequestDTO(
                account.getId(),
                user.getId(),
                user.getName(),
                account.getBalance(),
                type,
                amount
        );

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(transactionServiceUrl, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                if (type.equalsIgnoreCase("deposit")) {
                    account.setBalance(account.getBalance() + amount);
                } else if (type.equalsIgnoreCase("withdraw")) {
                    account.setBalance(account.getBalance() - amount);
                }
                return accountRepository.save(account);
            } else {
                throw new RuntimeException("Transaction Failed: " + response.getBody());
            }

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Transaction Failed: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred during transaction processing.", e);
        }
    }
}

