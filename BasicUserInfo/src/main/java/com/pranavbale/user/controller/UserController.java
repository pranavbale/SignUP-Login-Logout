package com.pranavbale.user.controller;

import com.pranavbale.user.entity.LoginRequest;
import com.pranavbale.user.entity.User;
import com.pranavbale.user.entity.UserResponse;
import com.pranavbale.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    private ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    private ResponseEntity<UserResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/logout/{email}")
    private ResponseEntity<String> logoutUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.logoutUser(email), HttpStatus.OK);
    }
}
