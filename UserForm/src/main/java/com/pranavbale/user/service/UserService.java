package com.pranavbale.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranavbale.user.entity.*;
import com.pranavbale.user.exception.InvalidInputParameterException;
import com.pranavbale.user.repository.AttachmentRepository;
import com.pranavbale.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


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
        return mapToUserResponse(saveUser);
    }

    // Login user using a user name and password
    public String loginUser(LoginRequest loginRequest) {

        // get the user form the database
        User user = userRepository.getByEmail(loginRequest.getEmail());

        // check user login status
        if (user.getIsLogin()) {
            throw new InvalidParameterException("User is already login " + user.getName());
        }

        // check user has correct password
        if (user.getPassword().equals(loginRequest.getPassword())) {
            user.setIsLogin(true);
            userRepository.save(user);
            return "User login Successfully";
        }
        // if not then send a response
        return "Please enter correct Input";
    }

    // logout the existing user
    public String logoutUser(UUID id) {

        // get a user form the database
        User user = userRepository.findById(id).orElseThrow(() -> new InvalidParameterException("Please enter a valid Id"));

        // check the login status of user
        if (user.getIsLogin()) {
            user.setIsLogin(false);
        }else {
            throw new InvalidInputParameterException(("User is already logout " + id));
        }

        // return a response
        return "Usr LogOut successfully " + user.getName();
    }


    // get All user Information
    public List<UserResponse> getAllUser() {

        // get all users form the database
        List<User> users = (List<User>) userRepository.findAll();

        // Use Java streams to map User objects to UserResponse objects

        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    // save photo into the database
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
					.fileName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())))
					.fileType(file.getContentType())
					.data(file.getBytes())
					.build();
		} catch (Exception e) {
			throw new InvalidParameterException(e.getMessage());
		}
    	
    	attachment = attachmentRepository.save(attachment);
    	
    	return "Data save successfully Id : " + attachment.getId();
    }

    // get a photo form the database
    public AttachmentResponse getPhotoInfo( UUID id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(()-> new InvalidParameterException("Please enter a valid Id"));
        return AttachmentResponse.builder()
                .id(attachment.getId())
                .userName(attachment.getUserName())
                .userMobileNumber(attachment.getUserMobileNumber())
                .fileName(attachment.getFileName())
                .fileType(attachment.getFileType())
                .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    public Attachment getAttachment(UUID id) {
        return attachmentRepository.findById(id).orElseThrow(()-> new InvalidParameterException("Please enter a valid Id"));
    }
}
