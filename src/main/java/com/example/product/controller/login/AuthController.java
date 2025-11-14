package com.example.product.controller.login;

import com.example.product.config.JwtUtils;
import com.example.product.dto.AuthRequestDTO;
import com.example.product.dto.AuthResponseDTO;
import com.example.product.model.User;
import com.example.product.repository.UserRepository;
import com.example.product.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
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
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody AuthRequestDTO dto) {
        try {
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

        } catch (BadCredentialsException e) {
            // Handle bad credentials
            ApiResponse<String> errorResponse = new ApiResponse<>(false,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid username or password",
                    null);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            // Handle general exceptions (e.g., unexpected errors)
            ApiResponse<String> errorResponse = new ApiResponse<>(false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while processing your request",
                    null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
