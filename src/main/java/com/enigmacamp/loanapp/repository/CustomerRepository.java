package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
