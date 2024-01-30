package com.pranavbale.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.pranavbale.user.entity.Attachment;
import com.pranavbale.user.entity.AttachmentResponse;
import com.pranavbale.user.entity.LoginRequest;
import com.pranavbale.user.entity.User;
import com.pranavbale.user.entity.UserResponse;
import com.pranavbale.user.repository.AttachmentRepository;
import com.pranavbale.user.repository.UserRepository;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    AttachmentRepository attachmentRepository;

    // Create a user
    public UserResponse createUser(User user) {

        //set a user to logout
        user.setIsLogin(false);

        // save user in to the database
        User saveUser = userRepository.save(user);

        // return a userResponse
        return UserResponse.builder()
                .userId(saveUser.getUserId())
                .name(saveUser.getName())
                .phoneNumber(saveUser.getPhoneNumber())
                .email(saveUser.getEmail())
                .build();
    }

    // Login user using a user name and password
    public String loginUser(LoginRequest loginRequest) {

        // get the user form the database
        User user = userRepository.getByEmail(loginRequest.getEmail());

        // check user has correct password
        if (user.getPassword().equals(loginRequest.getPassword())) {
            user.setIsLogin(true);
            userRepository.save(user);
            return "User login Successfully";
        }
        // if not then send a response
        return "Please enter correct Input";
    }

    public String savePhoto(MultipartFile file, String detail) {
    	
    	// object mapper is used to add string(json) data into the class 
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	Attachment attachment = null;
    	
    	try {
    		
    		// store user information into the Attachment
    		attachment = objectMapper.readValue(detail, Attachment.class);
    		
			attachment = Attachment.builder()
					.userName(attachment.getUserName())
					.userMobileNumber(attachment.getUserMobileNumber())
					.fileName(StringUtils.cleanPath(file.getOriginalFilename()))
					.fileType(file.getContentType())
					.data(file.getBytes().toString())
					.build();
		} catch (Exception e) {
			throw new InvalidParameterException(e.getMessage());
		}
    	
    	attachment = attachmentRepository.save(attachment);
    	
    	return "Data save successfully Id : " + attachment.getId();
    }
}
