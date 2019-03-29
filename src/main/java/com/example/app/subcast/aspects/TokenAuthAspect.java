package com.example.app.subcast.aspects;

import com.example.app.subcast.controllers.CommonResponses;
import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Token;
import com.example.app.subcast.repositories.AccountRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAuthAspect implements CommonResponses {
    private AccountRepository accountRepository;

    @Autowired
    public TokenAuthAspect(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Around(value = "execution(* com.example.app.subcast.controllers.*.*(token))", argNames = "pjp,token")
    public Object authorizeByToken(ProceedingJoinPoint pjp, Token token) throws Throwable {
        System.out.println("INVOKE ASPECT");
        Account account = accountRepository.findAccountByToken(token);
        if (account != null) {
            return pjp.proceed(new Object[]{token});
        } else {
            return INVALID_TOKEN;
        }
    }
}
