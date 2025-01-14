package com.bazarweb.bazarweb.service;

import com.bazarweb.bazarweb.DTO.JwtAuthenticationResponse;
import com.bazarweb.bazarweb.DTO.SignInRequest;
import com.bazarweb.bazarweb.DTO.SignUpRequest;
import com.bazarweb.bazarweb.JWT.JwtService;
import com.bazarweb.bazarweb.enums.UserRole;
import com.bazarweb.bazarweb.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(UserRole.USER)
                        .build();

        userService.userCreate(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
    
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());
    
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
    
}
