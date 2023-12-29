package com.magicpostapi.services;

import com.magicpostapi.components.AuthenticationResponse;
import com.magicpostapi.models.CustomUserDetails;
import com.magicpostapi.models.User;
import com.magicpostapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {
        @Autowired
        private UserRepository repository;
        @Autowired
        private JwtService jwtService;
        @Autowired
        private AuthenticationManager authenticationManager;

        public AuthenticationResponse authenticate(Map<String, String> user) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                user.get("email"),
                                                user.get("password")));
                CustomUserDetails userFound = new CustomUserDetails(repository.findUserByEmail(user.get("email"))
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "User not found with email : " + user.get("email"))));
                User userEntity = userFound.getUser();
                String jwtToken = jwtService.generateToken(userFound);
                return new AuthenticationResponse(userEntity.getId(), userEntity.getName(), userEntity.getPhone(),
                                userEntity.getLocation(), userEntity.getEmail(), jwtToken, userEntity.getRole());
        }
}
