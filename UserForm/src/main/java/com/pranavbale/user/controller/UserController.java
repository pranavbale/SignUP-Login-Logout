package com.pranavbale.user.controller;

import com.pranavbale.user.entity.LoginRequest;
import com.pranavbale.user.entity.User;
import com.pranavbale.user.entity.UserResponse;
import com.pranavbale.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    private ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/savePhoto")
    private ResponseEntity<String> savePhoto(@RequestParam("photo") MultipartFile multipartFile, @RequestParam("detail") String detail) {
        return new ResponseEntity<>(userService.savePhoto(multipartFile, detail), HttpStatus.CREATED);
    }

}
