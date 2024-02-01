package com.pranavbale.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue
    private UUID id;
    // related to user
    private String userName;
    private String userMobileNumber;
    // related to file
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;
}
