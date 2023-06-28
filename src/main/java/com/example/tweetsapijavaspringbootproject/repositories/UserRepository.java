package com.example.tweetsapijavaspringbootproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tweetsapijavaspringbootproject.models.UserModel;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

}