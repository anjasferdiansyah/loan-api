package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.dto.request.LoanRequestByAdminOrStaff;
import com.enigmacamp.loanapp.dto.request.LoanTransactionRequest;
import com.enigmacamp.loanapp.entity.LoanTransaction;
import com.enigmacamp.loanapp.entity.LoanType;

import java.util.List;

public interface LoanTransactionService {
    LoanTransaction createNew(LoanTransactionRequest loanTransaction);
    List<LoanTransaction> findAll();
    LoanTransaction findById(String id);
    LoanTransaction update(LoanRequestByAdminOrStaff loanTransaction);
    void delete(String id);
}
