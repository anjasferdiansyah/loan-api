package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.dto.request.AuthRequest;
import com.enigmacamp.loanapp.dto.response.LoginResponse;
import com.enigmacamp.loanapp.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest authRequest);

    LoginResponse login (AuthRequest authRequest);

    RegisterResponse registerAdmin(AuthRequest authRequest);
}
