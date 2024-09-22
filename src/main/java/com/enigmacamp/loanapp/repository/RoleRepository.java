package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.constant.ERole;
import com.enigmacamp.loanapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
