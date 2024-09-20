package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.entity.LoanType;

import java.util.List;

public interface LoanTypeService {
    LoanType createNew(LoanType loanType);
    List<LoanType> findAll();
    LoanType findById(String  id);
    LoanType update(LoanType loanType);
    void delete(String id);
}
