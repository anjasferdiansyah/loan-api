package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.entity.LoanType;
import com.enigmacamp.loanapp.repository.LoanTypeRepository;
import com.enigmacamp.loanapp.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {

    private final LoanTypeRepository loanTypeRepository;

    @Override
    public LoanType createNew(LoanType loanType) {
        return loanTypeRepository.save(loanType);
    }

    @Override
    public List<LoanType> findAll() {
        return loanTypeRepository.findAll();
    }

    public LoanType findByIdOrThrow(String  id) {
        return loanTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Type not found"));
    }

    @Override
    public LoanType findById(String id) {
        return findByIdOrThrow(id);
    }

    @Override
    public LoanType update(LoanType loanType) {
        findByIdOrThrow(loanType.getId());
        return loanTypeRepository.save(loanType);
    }

    @Override
    public void delete(String id) {
        findByIdOrThrow(id);
        loanTypeRepository.deleteById(id);
    }
}
