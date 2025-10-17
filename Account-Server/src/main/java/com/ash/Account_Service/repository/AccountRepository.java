package com.ash.Account_Service.repository;

import com.ash.Account_Service.entity.Account;
import com.ash.Account_Service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
}
