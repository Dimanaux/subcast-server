package com.example.app.subcast.services;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Token;
import com.example.app.subcast.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Component
@Service
public class AuthServiceImpl implements AuthService {
    private AccountRepository accountRepository;

    @Autowired
    public AuthServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Token authenticate(Account account) {
        String username = account.getUsername();
        String password = encrypt(account.getPassword());

        Account accountByUsername = accountRepository.findAccountByUsername(username);

        // wrong username
        if (accountByUsername == null) {
            return null;
        }

        // correct username, wrong password
        if (!accountByUsername.getPassword().equals(password)) {
            return null;
        }

        Token token = createToken(accountByUsername);
        accountRepository.createToken(accountByUsername, token);
        return token;
    }

    @Override
    public Account createAccount(Account account) {
        if (accountRepository.findAccountByUsername(account.getUsername()) == null) {
            accountRepository.createAccount(account);
            return account;
        } else {
            return null;
        }
    }

    private Token createToken(Account account) {
        String token = encrypt(account.getUsername() + LocalDateTime.now());
        return new Token(token);
    }

    private String encrypt(String password) {
        // todo add real encryption
        return password;
    }
}
