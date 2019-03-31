package com.example.app.subcast.controllers;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Podcast;
import com.example.app.subcast.db.Token;
import com.example.app.subcast.db.repositories.AccountRepository;
import com.example.app.subcast.db.repositories.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path = {"/subscriptions"})
public class SubscriptionsController implements CommonResponses {
    private final AccountRepository accountRepository;
    private final PodcastRepository podcastRepository;

    @Autowired
    public SubscriptionsController(AccountRepository accountRepository, PodcastRepository podcastRepository) {
        this.accountRepository = accountRepository;
        this.podcastRepository = podcastRepository;
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map getSubscriptions(@RequestBody Token token) {
        Account account = accountRepository.findByToken(token);
        if (account != null) {
            return Map.of(
                    "status", "OK",
                    "response", account.getSubscriptions()
            );
        } else {
            return INVALID_TOKEN;
        }
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map addSubscription(@RequestBody Map<String, String> body) {
        Token token = new Token(body.get("token"));
        String feedUrl = body.get("podcastFeedUrl");
        long podcastId = Long.parseLong(body.get("podcastId"));

        Account account = accountRepository.findByToken(token);
        if (account != null) {
            if (feedUrl != null && !feedUrl.isEmpty()) {
                podcastRepository.save(new Podcast(podcastId, feedUrl));
            }
            podcastRepository.createSubscription(account.getId(), podcastId);
            return STATUS_OK;
        } else {
            return INVALID_TOKEN;
        }
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map deleteSubscription(@RequestBody Map<String, String> body) {
        Token token = new Token(body.get("token"));
        long podcastId = Long.parseLong(body.get("podcastId"));

        Account account = accountRepository.findByToken(token);
        if (account != null) {
            podcastRepository.deleteSubscription(account.getId(), podcastId);
            return STATUS_OK;
        } else {
            return INVALID_TOKEN;
        }
    }
}
