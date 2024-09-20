package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.dto.request.LoanRequestByAdminOrStaff;
import com.enigmacamp.loanapp.dto.request.LoanTransactionRequest;
import com.enigmacamp.loanapp.entity.LoanTransaction;
import com.enigmacamp.loanapp.entity.LoanTransactionDetail;

import java.util.List;

public interface LoanTransactionDetailService {
    LoanTransactionDetail createNew(LoanTransactionDetail loanTransaction);
    List<LoanTransactionDetail> findAll();
    LoanTransactionDetail findById(String id);
    LoanTransactionDetail update(LoanTransactionDetail loanTransaction);
    void delete(String id);
}
