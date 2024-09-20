package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.entity.InstallmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentTypeRepository extends JpaRepository<InstallmentType, String > {

}