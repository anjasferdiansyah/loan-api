package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTypeRepository extends JpaRepository<LoanType, String > {
}
