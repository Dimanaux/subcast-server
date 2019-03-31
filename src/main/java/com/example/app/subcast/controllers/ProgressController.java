package com.example.app.subcast.controllers;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Token;
import com.example.app.subcast.db.repositories.AccountRepository;
import com.example.app.subcast.db.repositories.ProgressRepository;
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
    public Map getProgress(@RequestBody Map<String, String> body) {
        Token token = new Token(body.get("token"));
        Account account = accountRepository.findByToken(token);
        if (account != null) {
            if (body.containsKey("guid")) {
                return Map.of(
                        "status", "OK",
                        "progresses", progressRepository.findByAccountIdAndGuid(account.getId(), body.get("guid"))
                );
            } else {
                return Map.of(
                        "status", "OK",
                        "progresses", progressRepository.findAllByAccountId(account.getId())
                );
            }
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
    public Map updateProgress(@RequestBody Map<String, String> body) {
        Token token = new Token(body.get("token"));
        String guid = body.get("guid");
        int time = Integer.parseInt(body.get("time"));

        Account account = accountRepository.findByToken(token);
        if (account != null) {
            progressRepository.saveProgress(account.getId(), guid, time);
            return STATUS_OK;
        } else {
            return INVALID_TOKEN;
        }
    }
}
