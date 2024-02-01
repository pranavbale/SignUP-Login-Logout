package com.pranavbale.user.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

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

}
