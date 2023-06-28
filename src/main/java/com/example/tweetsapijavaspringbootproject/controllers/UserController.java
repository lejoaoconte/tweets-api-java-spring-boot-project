package com.example.tweetsapijavaspringbootproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.tweetsapijavaspringbootproject.repositories.UserRepository;

@RestController
public class UserController {

  @Autowired
  UserRepository userRepository;

  
}
