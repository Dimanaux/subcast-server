package com.example.app.subcast.services;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Token;

public interface AuthService {
    Token authenticate(Account account);

    Account createAccount(Account account);
}
