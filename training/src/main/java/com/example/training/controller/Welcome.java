package com.example.training.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class Welcome {

    @GetMapping("/")
    public String index(){
        return "Welcome";
    }
}
