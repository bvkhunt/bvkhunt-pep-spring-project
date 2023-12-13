package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account getAccount(Account account){
        Optional<Account> fetchedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(fetchedAccount.isPresent()){
            return fetchedAccount.get();
        }
        else{
            return null;
        }
    }


    public Boolean isExstingAccountById(Integer account_id){
        return accountRepository.existsById(account_id);
    }
    
    public Account registerAccount(Account account){
        return accountRepository.save(account);
    }

    public Boolean isExistingAccount(String account){
        return accountRepository.existsByUsername(account);
    }

    public Boolean isValidAccount(Account account){
        return isValidUsername(account.getUsername()) && isValidPassword(account.getPassword());
    }

    public Boolean isValidUsername(String username){
        return username != null && !username.isEmpty();
    }
    
    public Boolean isValidPassword(String password){
        return password != null && !password.isEmpty() && password.length() > 3;
    }
}
