package com.example.careplane.auth;

import com.example.careplane.config.JwtService;
import com.example.careplane.dto.AdminDto;
import com.example.careplane.dto.Response;
import com.example.careplane.entity.Admin;
import com.example.careplane.entity.Role;
import com.example.careplane.entity.User;
import com.example.careplane.listener.RegistrationCompleteEvent;
import com.example.careplane.repository.UserRepository;
import com.example.careplane.token.Token;
import com.example.careplane.token.TokenRepository;
import com.example.careplane.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.careplane.services.UserService.applicationUrl;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher publisher;

   /* public Response register(RegisterRequest userRequest, final HttpServletRequest request) {

        if (repository.existsByEmail(userRequest.getEmail())) {
            return Response.builder()
                    .responseMessage("User with provided email already exists!")
                    .build();
        }

        User user;

        if (userRequest instanceof AdminDto) {
            user = AdminDto.toEntity((AdminDto) userRequest);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.Admin);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

        User savedUser = repository.save(user);

        publisher.publishEvent(new RegistrationCompleteEvent((Admin) savedUser, applicationUrl(request)));

        return Response.builder()
                .responseMessage("User registered successfully")
                .email(user.getEmail())
                .build();
    }*/
   public Response register(RegisterRequest userRequest, final HttpServletRequest request) {

       if (repository.existsByEmail(userRequest.getEmail())) {
           return Response.builder()
                   .responseMessage("User with provided email already exists!")
                   .build();
       }

       User user;

       if (userRequest instanceof AdminDto) {
           user = AdminDto.toEntity((AdminDto) userRequest);
           String password = user.getPassword();
           if (password == null) {
               throw new IllegalArgumentException("Password cannot be null");
           }
           user.setPassword(passwordEncoder.encode(password));
           user.setRole(Role.Admin);
       } else {
           throw new IllegalArgumentException("Invalid user type");
       }

       User savedUser = repository.save(user);

       publisher.publishEvent(new RegistrationCompleteEvent((Admin) savedUser, applicationUrl(request)));

       return Response.builder()
               .responseMessage("User registered successfully")
               .email(user.getEmail())
               .build();
   }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = repository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}

