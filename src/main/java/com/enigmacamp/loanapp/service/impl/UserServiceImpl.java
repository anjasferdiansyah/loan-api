package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.entity.AppUser;
import com.enigmacamp.loanapp.entity.User;
import com.enigmacamp.loanapp.repository.UserRepository;
import com.enigmacamp.loanapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
    }

    // Verifikasi JWT
    @Override
    public AppUser loadByUserId(String id) {
        User userCredential = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .email(userCredential.getEmail())
                .password(userCredential.getPassword())
                .roles(userCredential.getRoles())
                .build();
    }


    // Verifikasi authentikasi login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userCredential = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .email(userCredential.getEmail())
                .password(userCredential.getPassword())
                .roles(userCredential.getRoles())
                .build();
    }
}