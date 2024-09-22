package com.enigmacamp.loanapp.controller;
import com.enigmacamp.loanapp.constant.ApiPath;
import com.enigmacamp.loanapp.dto.response.CommonResponse;
import com.enigmacamp.loanapp.dto.response.UserResponse;
import com.enigmacamp.loanapp.entity.Role;
import com.enigmacamp.loanapp.entity.User;
import com.enigmacamp.loanapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPath.GET_USER_URL)
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUser(@PathVariable String id) {
        User byId = userService.findById(id);

        List<Role> roles = byId.getRoles();

        List<String> list = roles.stream().map(role -> role.getRole().name()).toList();

        UserResponse userResponse = UserResponse.builder()
                .email(byId.getEmail())
                .roles(list)
                .build();

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Success get user")
                .data(userResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
