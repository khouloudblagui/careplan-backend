package com.example.careplane.services;

import com.example.careplane.dto.Response;
import com.example.careplane.entity.User;
import com.example.careplane.entity.VerificationToken;
import com.example.careplane.repository.UserRepository;
import com.example.careplane.repository.VerificationTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import java.util.Calendar;
import java.util.UUID;

import static com.example.careplane.services.UserService.applicationUrl;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final HttpServletRequest servletRequest;

    @Override
    public void saveUserVerificationToken(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid verification token";
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "Token already expired";
        }
        user.setIsEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public ResponseEntity<Response> verifyEmail(String token) {
        String url = applicationUrl(servletRequest) + "/api/v1/verify/resend-verification-token?token=" + token;

        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnabled()) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseMessage("This account has already been verified, please login")
                            .email(verificationToken.getUser().getEmail())
                            .build()
            );
        }

        String verificationResult = this.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            return ResponseEntity.ok(
                    Response.builder()
                            .responseMessage("Email verified successfully. Now you can login to your account")
                            .email(verificationToken.getUser().getEmail())
                            .build()
            );
        }

        return ResponseEntity.ok(
                Response.builder()
                        .responseMessage("Invalid verification link, <a href=\"" + url + "\"> Get a new verification link. </a>")
                        .email(verificationToken.getUser().getEmail())
                        .build()
        );
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var verificationTokenTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(verificationTokenTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }
}
