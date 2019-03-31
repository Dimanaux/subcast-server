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
import java.util.TreeMap;

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
            return new TreeMap() {{
                put("status", "OK");
                put("response", new TreeMap() {{
                    put("token", token.toString());
                }});
            }};
        } else {
            return new TreeMap() {{
                put("status", "ERROR");
                put("errorMessage", "Authentication failed. Wrong username or password");
            }};
        }
    }
}
