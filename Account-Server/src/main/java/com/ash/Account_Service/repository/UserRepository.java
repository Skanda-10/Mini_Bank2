package com.ash.Account_Service.repository;

import com.ash.Account_Service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
