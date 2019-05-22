package com.springboot0513.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/hello")
public class HelloWord {

    @RequestMapping(value = "/world")
    public String hello(){
        return "Hello world";
    }
}
