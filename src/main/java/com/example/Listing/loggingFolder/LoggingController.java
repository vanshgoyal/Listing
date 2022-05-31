package com.example.Listing.loggingFolder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    @RequestMapping("/")
    public String index() {
        return "Welcome to Spring Logging!";
    }
}