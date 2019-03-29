package com.example.app.subcast.controllers;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Progress;
import com.example.app.subcast.db.Token;
import com.example.app.subcast.repositories.AccountRepository;
import com.example.app.subcast.repositories.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path = {"/progress"})
public class ProgressController implements CommonResponses {
    private final AccountRepository accountRepository;
    private final ProgressRepository progressRepository;

    @Autowired
    public ProgressController(AccountRepository accountRepository, ProgressRepository progressRepository) {
        this.accountRepository = accountRepository;
        this.progressRepository = progressRepository;
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Map getProgresses(@RequestBody Token token) {
        Account account = accountRepository.findAccountByToken(token);
        if (account != null) {
            return Map.of(
                    "status", "OK",
                    "progresses", progressRepository.findAllByAccountId(account.getId())
            );
        } else {
            return INVALID_TOKEN;
        }
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Map getProgress(@RequestBody Token token, @RequestParam(name = "link") String link) {
        Account account = accountRepository.findAccountByToken(token);
        if (account != null) {
            return Map.of(
                    "status", "OK",
                    "progresses", progressRepository.findByAccountAndLink(account.getId(), link)
            );
        } else {
            return INVALID_TOKEN;
        }
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Map updateProgress(@RequestBody Token token, @RequestParam(name = "link") String link, @RequestParam(name = "time") int time) {
        Account account = accountRepository.findAccountByToken(token);
        if (account != null) {
            progressRepository.updateProgress(account.getId(), link, time);
            return STATUS_OK;
        } else {
            return INVALID_TOKEN;
        }
    }
}
