package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.constant.ERole;
import com.enigmacamp.loanapp.dto.request.AuthRequest;
import com.enigmacamp.loanapp.dto.response.LoginResponse;
import com.enigmacamp.loanapp.dto.response.RegisterResponse;
import com.enigmacamp.loanapp.entity.*;
import com.enigmacamp.loanapp.repository.UserRepository;
import com.enigmacamp.loanapp.security.JwtUtil;
import com.enigmacamp.loanapp.service.AuthService;
import com.enigmacamp.loanapp.service.CustomerService;
import com.enigmacamp.loanapp.service.RoleService;
//import com.enigmacamp.loanapp.util.validation.ValidationUtil;
import com.enigmacamp.loanapp.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final StaffService staffService;
    private final RoleService roleService;
//    private final ValidationUtil validationUtil;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest authRequest) {
        try {
//            validationUtil.validate(authRequest);
            // Create Role
            Role role = roleService.getOrSave(Role.builder()
                    .role(ERole.ROLE_CUSTOMER)
                    .build());
            // Create User Credential

            List<Role> roleList = new ArrayList<>();
            roleList.add(role);

            User userCredential = User.builder()
                    .email(authRequest.getEmail())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .roles(roleList)
                    .build();

            userRepository.saveAndFlush(userCredential);

            //Customer

            Customer customer = Customer.builder()
                    .user(userCredential)
                    .build();

            customerService.createNewCustomer(customer);

            List<String> roles = customer.getUser().getRoles().stream().map(r -> r.getRole().name()).toList();

            return RegisterResponse.builder()
                    .email(customer.getUser().getEmail())
                    .roles(roles)
                    .build();

        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }



    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse registerAdmin(AuthRequest authRequest) {
        try {

            // Create Role
            Role admin = roleService.getOrSave(Role.builder()
                    .role(ERole.ROLE_ADMIN)
                    .build());

            Role staff = roleService.getOrSave(Role.builder()
            .role(ERole.ROLE_STAFF)
                    .build());

            List<Role> roleList = new ArrayList<>();
            roleList.add(admin);
            roleList.add(staff);
            // Create User Credential



            User userCredential = User.builder()
                    .email(authRequest.getEmail())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .roles(roleList)
                    .build();

            userRepository.saveAndFlush(userCredential);

            Staff newStaff = Staff.builder()
                    .user(userCredential)
                    .build();
            staffService.createNew(newStaff);

            return RegisterResponse.builder()
                    .email(newStaff.getUser().getEmail())
                    .roles(newStaff.getUser().getRoles().stream().map(r -> r.getRole().name()).toList())
                    .build();

        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }



    @Override
    public LoginResponse login(AuthRequest authRequest) throws AuthenticationException {
        //tempat logic untuk login
//        validationUtil.validate(authRequest);
//        log.info("username : " + authRequest.getUsername());
//        log.info("password : " + authRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // object AppUser
        AppUser appUser = (AppUser) authenticate.getPrincipal();
        String token = jwtUtil.generateToken(appUser);
        log.info("token: {}", token);

        return LoginResponse.builder()
                .token(token)
                .email(appUser.getEmail())
                .role(appUser.getRoles().toString())
                .build();
    }
}
