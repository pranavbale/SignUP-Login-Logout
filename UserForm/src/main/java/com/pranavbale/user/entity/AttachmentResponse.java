package com.pranavbale.user.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentResponse {
	
	private UUID id;
    private String userName;
    private String userMobileNumber;
    private String fileName;
    private String fileType;
    private String url;

}
