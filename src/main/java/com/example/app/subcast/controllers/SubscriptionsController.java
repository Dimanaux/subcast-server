package com.example.app.subcast.controllers;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Token;
import com.example.app.subcast.repositories.AccountRepository;
import com.example.app.subcast.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path = {"/subscriptions"})
public class SubscriptionsController implements CommonResponses {
    private final AccountRepository accountRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionsController(AccountRepository accountRepository, SubscriptionRepository subscriptionRepository) {
        this.accountRepository = accountRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map getSubscriptions(@RequestBody Token token) {
        Account account = accountRepository.findAccountByToken(token);
        if (account != null) {
            return Map.of(
                    "status", "OK",
                    "response", subscriptionRepository.findAllByAccountId(account.getId())
            );
        } else {
            return INVALID_TOKEN;
        }
    }

    @ResponseBody
    @RequestMapping(
            path = "/{podcastId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map addSubscription(@RequestBody Token token, @PathVariable int podcastId) {
        Account account = accountRepository.findAccountByToken(token);
        if (account != null) {
            subscriptionRepository.createSubscription(account.getId(), podcastId);
            return STATUS_OK;
        } else {
            return INVALID_TOKEN;
        }
    }

    @ResponseBody
    @RequestMapping(
            path = "/{podcastId}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map deleteSubscription(@RequestBody Token token, @PathVariable int podcastId) {
        Account account = accountRepository.findAccountByToken(token);
        if (account != null) {
            subscriptionRepository.deleteSubscription(account.getId(), podcastId);
            return STATUS_OK;
        } else {
            return INVALID_TOKEN;
        }
    }

}
