package com.magicpost.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.magicpost.components.CustomUserDetails;
import com.magicpost.exception.ResourseNotFoundException;
import com.magicpost.model.User;
import com.magicpost.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourseNotFoundException("User", "username", username));
        return new CustomUserDetails(user);
    }

}
