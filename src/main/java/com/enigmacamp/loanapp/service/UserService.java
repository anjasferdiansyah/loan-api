package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.entity.AppUser;
import com.enigmacamp.loanapp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadByUserId(String userId);
    User findById(String userId);
}
