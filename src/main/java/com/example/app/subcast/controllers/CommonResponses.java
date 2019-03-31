package com.example.app.subcast.controllers;

import java.util.Map;
import java.util.TreeMap;

public interface CommonResponses {
    Map<String, String> INVALID_TOKEN = new TreeMap<String, String>() {{
        put("status", "ERROR");
        put("errorMessage", "Invalid token.");
    }};
    Map<String, String> STATUS_OK = new TreeMap<String, String>() {{
        put("status", "OK");
    }};
}
