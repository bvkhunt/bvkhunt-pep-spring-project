package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    @Query("SELECT a FROM Account a WHERE a.username = :username AND a.password =:password")
    Optional<Account> findByUsernameAndPassword(String username, String password);
    List<Account> findByUsername(String username);

    Boolean existsByUsername(String account);

    Optional<Account> findById(Integer account_id);
}
