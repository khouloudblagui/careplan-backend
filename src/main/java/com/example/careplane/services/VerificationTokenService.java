package com.example.careplane.services;

import com.example.careplane.dto.Response;
import com.example.careplane.entity.User;
import com.example.careplane.entity.VerificationToken;
import org.springframework.http.ResponseEntity;

public interface VerificationTokenService {
    void saveUserVerificationToken(User user, String token);
    String validateToken(String token);
    ResponseEntity<Response> verifyEmail(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
}
