package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private Date expiresAt; // token expiration time
}
