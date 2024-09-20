package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.ApiPath;
import com.enigmacamp.loanapp.dto.request.AuthRequest;
import com.enigmacamp.loanapp.dto.response.CommonResponse;
import com.enigmacamp.loanapp.dto.response.LoginResponse;
import com.enigmacamp.loanapp.dto.response.RegisterResponse;
import com.enigmacamp.loanapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.AUTH_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiPath.REGISTER_URL)
    public ResponseEntity<?> registerCustomer (@RequestBody AuthRequest request) {

        RegisterResponse registerResponse = authService.registerCustomer(request);

        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .message("Successfully registered customer.")
                .status(HttpStatus.CREATED.value())
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }

    @PostMapping(ApiPath.LOGIN_URL)
    public ResponseEntity<?> loginCustomer (@RequestBody AuthRequest request) {

        LoginResponse loginResponse = authService.login(request);


        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .message("Successfully registered customer.")
                .status(HttpStatus.OK.value())
                .data(loginResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


//    @PostMapping("/register/admin")
//    public ResponseEntity<?> registerAdmin (@RequestBody AuthRequest request) {
//
//        RegisterResponse registerResponse = authService.registerAdmin(request);
//
//        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
//                .message("Successfully registered admin.")
//                .status(HttpStatus.CREATED.value())
//                .data(registerResponse)
//                .build();
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//
//
//    }

}
