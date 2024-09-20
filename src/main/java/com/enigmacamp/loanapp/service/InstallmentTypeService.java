package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.entity.InstallmentType;

import java.util.List;

public interface InstallmentTypeService {
    InstallmentType createNewInstallmentType(InstallmentType installmentType);
    InstallmentType findById(String id);
    List<InstallmentType> findAll();
    InstallmentType update(InstallmentType installmentType);
    void delete(String id);
}
