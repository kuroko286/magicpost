package com.magicpostapi.services;

import com.magicpostapi.models.CustomUserDetails;
import com.magicpostapi.models.User;
import com.magicpostapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email));
        if (user == null)
            throw new UsernameNotFoundException(email);
        return new CustomUserDetails(user);
    }
}
