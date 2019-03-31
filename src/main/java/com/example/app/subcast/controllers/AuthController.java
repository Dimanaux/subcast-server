package com.example.app.subcast.controllers;

import com.example.app.subcast.db.Account;
import com.example.app.subcast.db.Token;
import com.example.app.subcast.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(path = {"/auth"})
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Map authenticate(@RequestBody Account account) {
        Token token = authService.authenticate(account);
        if (token != null) {
            return Map.of(
                    "status", "OK",
                    "response", Map.of("token", token.toString())
            );
        } else {
            return Map.of(
                    "status", "ERROR",
                    "errorMessage", "Authentication failed. Wrong username or password"
            );
        }
    }
}
