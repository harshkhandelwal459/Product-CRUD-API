package com.example.product.controller.login;

import com.example.product.config.JwtUtils;
import com.example.product.dto.AuthRequestDTO;
import com.example.product.dto.AuthResponseDTO;
import com.example.product.model.User;
import com.example.product.repository.UserRepository;
import com.example.product.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication API")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    @Operation(
            summary = "Register new user",
            description = "This endpoint allows users to register a new account with a username and password."
    )
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody AuthRequestDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false,
                    HttpStatus.BAD_REQUEST.value(),
                    "Username already exists",
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(User.Role.USER)
                .build();

        userRepository.save(user);

        ApiResponse<Void> response = new ApiResponse<>(true,
                HttpStatus.CREATED.value(),
                "User registered successfully",
                null);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "This endpoint allows users to log in using their username and password. On successful authentication, a JWT token is generated."
    )
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO dto) {

        // Attempt authentication
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        // If authentication is successful, generate JWT token
        String token = jwtUtils.generateToken(dto.getUsername());

        // Calculate token expiration time (30 minutes)
        Date expirationTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        AuthResponseDTO authResponse = new AuthResponseDTO(token, expirationTime);

        ApiResponse<AuthResponseDTO> response = new ApiResponse<>(true,
                HttpStatus.OK.value(),
                "Login successful",
                authResponse);

        return ResponseEntity.ok(response);
    }

}
