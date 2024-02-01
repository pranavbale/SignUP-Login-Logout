package com.pranavbale.user.controller;

import com.pranavbale.user.entity.*;
import com.pranavbale.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // Create a New user
    @PostMapping("/signUp")
    private ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    // get all the user form the database
    @GetMapping("/getAll")
    private ResponseEntity<List<UserResponse>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    // login the user existing user
    @PostMapping("/login")
    private ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/logout/{id}")
    private ResponseEntity<String> logoutUser(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.logoutUser(id), HttpStatus.OK);
    }

    // save the user photo
    @PostMapping("/savePhoto")
    private ResponseEntity<String> savePhoto(@RequestParam("photo") MultipartFile multipartFile, @RequestParam("detail") String detail) {
        return new ResponseEntity<>(userService.savePhoto(multipartFile, detail), HttpStatus.CREATED);
    }

    // get the user photo form the database
    @GetMapping("/getPhotoInfo/{id}")
    private ResponseEntity<AttachmentResponse> getPhoto(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.getPhotoInfo(id), HttpStatus.OK);
    }

    @GetMapping("/downloadPhoto/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable UUID id) {
        Attachment attachment = null;
        attachment = userService.getAttachment(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=/" + attachment.getFileName() + "/")
                .body(new ByteArrayResource(attachment.getData()));
    }


}
