package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.entity.InstallmentType;
import com.enigmacamp.loanapp.repository.InstallmentTypeRepository;
import com.enigmacamp.loanapp.service.InstallmentTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentTypeServiceImpl implements InstallmentTypeService {

    private final InstallmentTypeRepository installmentTypeRepository;


    @Override
    public InstallmentType createNewInstallmentType(InstallmentType installmentType) {
        return installmentTypeRepository.save(installmentType);
    }

    public InstallmentType findByIdOrThrow(String id) {
        return installmentTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Installment type not found"));
    }

    @Override
    public InstallmentType findById(String id) {
       return findByIdOrThrow(id);
    }

    @Override
    public List<InstallmentType> findAll() {
        return installmentTypeRepository.findAll();
    }

    @Override
    public InstallmentType update(InstallmentType installmentType) {
        findByIdOrThrow(installmentType.getId());
        return installmentTypeRepository.save(installmentType);
    }

    @Override
    public void delete(String id) {
        findByIdOrThrow(id);
        installmentTypeRepository.deleteById(id);
    }
}
