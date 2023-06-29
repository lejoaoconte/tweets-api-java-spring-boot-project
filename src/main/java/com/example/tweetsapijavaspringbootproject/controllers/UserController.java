package com.example.tweetsapijavaspringbootproject.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tweetsapijavaspringbootproject.dtos.UserRecordDto;
import com.example.tweetsapijavaspringbootproject.models.UserModel;
import com.example.tweetsapijavaspringbootproject.repositories.UserRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {

  @Autowired
  UserRepository userRepository;

  @PostMapping(value = "/user")
  public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
    var userModel = new UserModel();
    BeanUtils.copyProperties(userRecordDto, userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
  }

  @GetMapping(value = "/user")
  public ResponseEntity<List<UserModel>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
  }

  @GetMapping(value = "/user/{id}")
  public ResponseEntity<UserModel> getUser(@PathVariable(value = "id") UUID id) {
    Optional<UserModel> user = userRepository.findById(id);
    if (user.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.status(HttpStatus.OK).body(user.get());
  }

}
