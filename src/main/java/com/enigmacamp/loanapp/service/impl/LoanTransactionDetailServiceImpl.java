package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.entity.LoanTransactionDetail;
import com.enigmacamp.loanapp.repository.LoanTransactionDetailRepository;
import com.enigmacamp.loanapp.service.LoanTransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTransactionDetailServiceImpl implements LoanTransactionDetailService {

    private final LoanTransactionDetailRepository loanTransactionDetailRepository;

    @Override
    public LoanTransactionDetail createNew(LoanTransactionDetail loanTransaction) {
        return loanTransactionDetailRepository.saveAndFlush(loanTransaction);
    }

    @Override
    public List<LoanTransactionDetail> findAll() {
        return loanTransactionDetailRepository.findAll();
    }

    public LoanTransactionDetail findByIdOrThrow(String id) {
        return loanTransactionDetailRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction Detail not found"));
    }

    @Override
    public LoanTransactionDetail findById(String id) {
        return findByIdOrThrow(id);
    }

    @Override
    public LoanTransactionDetail update(LoanTransactionDetail loanTransaction) {
        findById(loanTransaction.getId());
        return loanTransactionDetailRepository.save(loanTransaction);
    }

    @Override
    public void delete(String id) {
        loanTransactionDetailRepository.deleteById(id);
    }
}
