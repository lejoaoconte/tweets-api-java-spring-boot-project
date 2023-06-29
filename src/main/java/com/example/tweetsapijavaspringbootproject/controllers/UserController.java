package com.example.tweetsapijavaspringbootproject.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tweetsapijavaspringbootproject.dtos.UserRecordDto;
import com.example.tweetsapijavaspringbootproject.models.UserModel;
import com.example.tweetsapijavaspringbootproject.repositories.UserRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    List<UserModel> users = userRepository.findAll();
    if (!users.isEmpty()) {
      for (UserModel user : users) {
        UUID id = user.getIdUser();
        user.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
      }

    }
    return ResponseEntity.status(HttpStatus.OK).body(users);
  }

  @GetMapping(value = "/user/{id}")
  public ResponseEntity<Object> getUser(@PathVariable(value = "id") UUID id) {
    Optional<UserModel> user = userRepository.findById(id);
    if (user.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    user.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("All users"));
    return ResponseEntity.status(HttpStatus.OK).body(user.get());
  }

  @PutMapping(value = "/user/{id}")
  public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
      @RequestBody @Valid UserRecordDto userRecordDto) {
    Optional<UserModel> user = userRepository.findById(id);
    if (user.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    var userModel = user.get();
    BeanUtils.copyProperties(userRecordDto, userModel);
    return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
  }

  @DeleteMapping(value = "/user/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
    Optional<UserModel> user = userRepository.findById(id);
    if (user.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    userRepository.delete(user.get());
    return ResponseEntity.status(HttpStatus.OK).body("User deleted");
  }

}
