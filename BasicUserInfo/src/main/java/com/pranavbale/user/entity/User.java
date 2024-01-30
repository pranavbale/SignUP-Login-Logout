package com.pranavbale.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String email;
    @NonNull
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNo;
    @NonNull
    private String password;
    private Boolean isLogin;

}
