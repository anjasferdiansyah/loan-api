package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, String> {
}
