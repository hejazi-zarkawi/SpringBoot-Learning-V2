package com.hejazi.securityApp.securityApp.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PostDTO {

    private String title;

    private String description;

    private UserDTO author;
}
