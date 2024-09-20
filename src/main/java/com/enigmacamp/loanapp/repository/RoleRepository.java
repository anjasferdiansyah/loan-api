package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.constant.ERole;
import com.enigmacamp.loanapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole name);
}
