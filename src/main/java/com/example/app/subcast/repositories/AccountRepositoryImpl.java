package com.example.app.subcast.repositories;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private static final String createAccountQuery = "INSERT INTO account (username, password) VALUES (?, ?)";
    private static final String findAccountByUsernameQuery = "SELECT * FROM account WHERE username = ? LIMIT 1";
    private static final String saveTokenQuery = "INSERT INTO account_token (account_id, token) VALUES (?, ?)";
    private static final String findAccountByTokenQuery = "SELECT * FROM account a INNER JOIN account_token t on a.id = t.account_id WHERE token = ? LIMIT 1";

    private static final RowMapper<Account> accountMapper = (rs, i) -> new Account(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("password")
    );

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createAccount(Account account) {
        jdbcTemplate.update(createAccountQuery, account.getUsername(), account.getPassword());
    }

    @Override
    public Account findAccountByUsername(String username) {
        List<Account> result = jdbcTemplate.query(findAccountByUsernameQuery, accountMapper, username);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Account findAccountByToken(Token token) {
        List<Account> result = jdbcTemplate.query(findAccountByTokenQuery, accountMapper, token.toString());
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void createToken(Account account, Token token) {
        jdbcTemplate.update(saveTokenQuery, account.getId(), token.toString());
    }
}
