package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.constant.EApprovalStatus;
import com.enigmacamp.loanapp.constant.ELoanStatus;
import com.enigmacamp.loanapp.dto.request.LoanRequestByAdminOrStaff;
import com.enigmacamp.loanapp.dto.request.LoanTransactionRequest;
import com.enigmacamp.loanapp.entity.*;
import com.enigmacamp.loanapp.repository.LoanTransactionRepository;
import com.enigmacamp.loanapp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTransactionServiceImpl implements LoanTransactionService {

    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTransactionDetailService loanTransactionDetailService;
    private final LoanTypeService loanTypeService;
    private final InstallmentTypeService installmentTypeService;
    private final CustomerService customerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanTransaction createNew(LoanTransactionRequest loanTransaction) {
// Ambil LoanType, InstallmentType, dan Customer dari service yang sesuai
        LoanType loanType = loanTypeService.findById(loanTransaction.getLoanTypeId());
        InstallmentType installmentType = installmentTypeService.findById(loanTransaction.getInstallmentTypeId());
        Customer customer = customerService.getById(loanTransaction.getCustomerId());

// Buat LoanTransaction
        LoanTransaction transaction = LoanTransaction.builder()
                .instalmentType(installmentType)
                .customer(customer)
                .loanType(loanType)
                .nominal(loanTransaction.getNominal())
                .createdAt(Instant.now().toEpochMilli())
                .build();

// Simpan LoanTransaction terlebih dahulu
        LoanTransaction savedTransaction = loanTransactionRepository.saveAndFlush(transaction);

// Buat LoanTransactionDetail setelah LoanTransaction disimpan
        LoanTransactionDetail loanTransactionDetail = LoanTransactionDetail.builder()
                .loanStatus(ELoanStatus.PAID)
                .transactionDate(Instant.now().toEpochMilli())
                .createdAt(Instant.now().toEpochMilli())
                .loanTransaction(savedTransaction) // Referensikan LoanTransaction yang sudah disimpan
                .build();

// Simpan LoanTransactionDetail
        loanTransactionDetailService.createNew(loanTransactionDetail);

// Tambahkan LoanTransactionDetail ke LoanTransaction
        List<LoanTransactionDetail> transactionDetailList = new ArrayList<>();
        transactionDetailList.add(loanTransactionDetail);
        savedTransaction.setLoanTransactionDetails(transactionDetailList);

// Update dan simpan LoanTransaction dengan LoanTransactionDetails yang telah disetel
        LoanTransaction newLoanTransaction = loanTransactionRepository.save(savedTransaction);

        return loanTransactionRepository.save(newLoanTransaction);


    }

    @Override
    public List<LoanTransaction> findAll() {
        return loanTransactionRepository.findAll();
    }

    public LoanTransaction findByIdOrThrow(String id) {
       return loanTransactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Transaction Not Found"));
    }

    @Override
    public LoanTransaction findById(String id) {
        return findByIdOrThrow(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanTransaction update(LoanRequestByAdminOrStaff loanTransaction) {
        LoanTransaction foundedLoanTrx = findByIdOrThrow(loanTransaction.getLoanTransactionId());

        Double newNominal = (Double.parseDouble(loanTransaction.getInterestRate()))/100.0 + foundedLoanTrx.getNominal();

        foundedLoanTrx.setNominal(newNominal);
        foundedLoanTrx.setApprovalStatus(EApprovalStatus.APPROVED);
        foundedLoanTrx.setUpdatedAt(Long.parseLong(Instant.now().toString()));
        foundedLoanTrx.setApprovedAt(Long.parseLong(Instant.now().toString()));
        // Code di bawah untuk mendapatkan informasi user yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            foundedLoanTrx.setApprovedBy(email);
        }

        return loanTransactionRepository.save(foundedLoanTrx);
    }

    @Override
    public void delete(String id) {
        loanTransactionRepository.deleteById(id);
    }
}
