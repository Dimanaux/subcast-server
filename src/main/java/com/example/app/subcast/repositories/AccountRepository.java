package com.example.app.subcast.repositories;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Token;

public interface AccountRepository {
    void createAccount(Account account);

    Account findAccountByUsername(String username);

    Account findAccountByToken(Token token);

    void createToken(Account account, Token token);
}
