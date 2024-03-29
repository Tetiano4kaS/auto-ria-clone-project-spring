package com.autoriacloneprojectspring.services.security;

import com.autoriacloneprojectspring.auth.AuthenticationRequest;
import com.autoriacloneprojectspring.auth.AuthenticationResponse;
import com.autoriacloneprojectspring.auth.RegisterRequest;
import com.autoriacloneprojectspring.constant.AccountType;
import com.autoriacloneprojectspring.constant.Role;
import com.autoriacloneprojectspring.entity.Token;
import com.autoriacloneprojectspring.entity.User;
import com.autoriacloneprojectspring.exceptions.EmailAlreadyExistException;
import com.autoriacloneprojectspring.repository.TokenRepository;
import com.autoriacloneprojectspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    public AuthenticationResponse register(RegisterRequest request, Role role) {
        String email = request.getEmail();
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new EmailAlreadyExistException("User with this " + email + " already exist");
        });
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .accountType(AccountType.BASIC)
                .build();
        User savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User savedUser, String jwtTokenValue) {
        Token token = Token.builder()
                .tokenValue(jwtTokenValue)
                .user(savedUser)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        expiredToken(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void expiredToken(User user) {
        List<Token> allValidTokensByUser = tokenRepository.findAllValidTokensByUser(user.getId());
        if (allValidTokensByUser.isEmpty()) {
            return;
        }
        allValidTokensByUser.forEach(token -> token.setExpired(true));
        tokenRepository.saveAll(allValidTokensByUser);
    }
}
