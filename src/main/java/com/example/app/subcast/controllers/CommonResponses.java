package com.example.app.subcast.controllers;

import java.util.Map;

public interface CommonResponses {
    Map INVALID_TOKEN = Map.of("status", "ERROR", "errorMessage", "Invalid token.");
    Map STATUS_OK = Map.of("status", "OK");
}
