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

import static java.util.stream.Collectors.toList;

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
        int numberOfInstallmentType = 0;

        switch (installmentType.getInstallmentType()){
            case ONE_MONTH -> numberOfInstallmentType = 1;
            case THREE_MONTHS -> numberOfInstallmentType = 3;
            case NINE_MONTHS -> numberOfInstallmentType = 9;
            case TWELVE_MONTHS -> numberOfInstallmentType = 12;
        }

        List<LoanTransactionDetail> transactionDetailList = new ArrayList<>();

        for (int i = 1; i <= numberOfInstallmentType; i++){
            LoanTransactionDetail loanTransactionDetail = LoanTransactionDetail.builder()
                    .loanStatus(ELoanStatus.UNPAID)
                    .loanTransaction(savedTransaction)
                    .createdAt(Instant.now().toEpochMilli())
                    .build();
            transactionDetailList.add(loanTransactionDetail);
            loanTransactionDetailService.createNew(loanTransactionDetail);
        }

        savedTransaction.setLoanTransactionDetails(transactionDetailList);
        return loanTransactionRepository.save(savedTransaction);

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



        foundedLoanTrx.setNominal(foundedLoanTrx.getNominal());
        foundedLoanTrx.setApprovalStatus(EApprovalStatus.APPROVED);
        foundedLoanTrx.setUpdatedAt(Instant.now().toEpochMilli());
        foundedLoanTrx.setApprovedAt(Instant.now().toEpochMilli());
        // Code di bawah untuk mendapatkan informasi user yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            foundedLoanTrx.setApprovedBy(email);
        }

        Double interestRate = Double.parseDouble(loanTransaction.getInterestRates()) / 100.0;
        Double totalNominal = foundedLoanTrx.getNominal() * (1 + interestRate);
        Double numberOfInstallment = 0.0;

        InstallmentType installmentType = installmentTypeService.findById(foundedLoanTrx.getInstalmentType().getId());

        switch (installmentType.getInstallmentType()){
            case ONE_MONTH -> numberOfInstallment = 1.0;
            case THREE_MONTHS -> numberOfInstallment = 3.0;
            case NINE_MONTHS -> numberOfInstallment = 9.0;
            case TWELVE_MONTHS -> numberOfInstallment = 12.0;
        }


        List<LoanTransactionDetail> loanTransactionDetails = foundedLoanTrx.getLoanTransactionDetails();
        for (LoanTransactionDetail detail : loanTransactionDetails) {
            detail.setNominal(totalNominal/numberOfInstallment);
        }
        foundedLoanTrx.setLoanTransactionDetails(loanTransactionDetails);

        return loanTransactionRepository.saveAndFlush(foundedLoanTrx);
    }

    @Override
    public void delete(String id) {
        loanTransactionRepository.deleteById(id);
    }
}
