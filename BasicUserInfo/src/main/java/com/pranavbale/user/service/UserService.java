package com.pranavbale.user.service;

import com.pranavbale.user.entity.LoginRequest;
import com.pranavbale.user.entity.User;
import com.pranavbale.user.entity.UserResponse;
import com.pranavbale.user.exception.UserException;
import com.pranavbale.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserResponse createUser(User user) {

        User savedUser;

        // gating a user form the database if user is present with the same email id
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        // if present then throw error
        if (optionalUser.isPresent()) {
            throw new UserException("User already exit " + user.getEmail());
        }

        // if user is not present in the database then save new user in to database
        user.setIsLogin(false);
        savedUser = userRepository.save(user);

        // return a user details
        return UserResponse.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .address(savedUser.getAddress())
                .phoneNo(savedUser.getPhoneNo())
                .email(savedUser.getEmail())
                .build();
    }

    public UserResponse loginUser(LoginRequest loginRequest) {

        // get the user
        User user = getUserByEmail(loginRequest.getEmail());

        // if email and password of user is correct then user get login
        if (user.getEmail().equals(loginRequest.getEmail()) && user.getPassword().equals(loginRequest.getPassword())) {

            // check the user is already login if then throw exception
           if (user.getIsLogin()) {
               throw new UserException("User is already login : " + loginRequest.getEmail());
           } else {
               user.setIsLogin(true);
               user = userRepository.save(user);
           }
        }

        // return a user details
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phoneNo(user.getPhoneNo())
                .email(user.getEmail())
                .build();
    }

    public String logoutUser(String email) {

        // get a user
        User user = getUserByEmail(email);

        // check it is already logout if then throw an exception
        if (!user.getIsLogin()) {
            throw new UserException("User is already logout : " + user.getEmail());
        }

        // set user logout
        user.setIsLogin(false);
        userRepository.save(user);
        return user.getEmail() + " Logout Successfully Go To Login Page ";
    }

    private User getUserByEmail(String email) {
        // check user is present in the database or not
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));

        // if user is not present in data then throw an error else get a user
        if (optionalUser.isEmpty()) {
            throw new UserException("User is not found with email : " + email);
        }

        // return a user
        return  optionalUser.get();
    }


}
