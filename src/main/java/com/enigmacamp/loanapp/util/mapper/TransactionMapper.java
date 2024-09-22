package com.enigmacamp.loanapp.util.mapper;

import com.enigmacamp.loanapp.dto.response.TransactionDetailResponse;
import com.enigmacamp.loanapp.dto.response.TransactionResponse;
import com.enigmacamp.loanapp.entity.LoanTransaction;

import java.util.List;

public class TransactionMapper {
    public static TransactionResponse mapToLoanTransactionResponse(LoanTransaction newLoanTransaction) {

        List<TransactionDetailResponse> list = newLoanTransaction.getLoanTransactionDetails().stream().map((detail) -> TransactionDetailResponse.builder()
                .id(detail.getId())
                .loanStatus(detail.getLoanStatus().name())
                .updatedAt(detail.getUpdatedAt())
                .createdAt(detail.getCreatedAt())
                .nominal(detail.getNominal())
                .build()).toList();




        TransactionResponse build = TransactionResponse.builder()
                .id(newLoanTransaction.getId())
                .loanTypeId(newLoanTransaction.getLoanType().getId())
                .installmentTypeId(newLoanTransaction.getInstalmentType().getId())
                .customerId(newLoanTransaction.getCustomer().getId())
                .nominal(newLoanTransaction.getNominal())
                .approvedAt(newLoanTransaction.getApprovedAt())
                .approvedBy(newLoanTransaction.getApprovedBy())
                .transactionDetailResponse(list)
                .createdAt(newLoanTransaction.getCreatedAt())
                .build();

        if(newLoanTransaction.getApprovalStatus() != null) {
            build.setApprovalStatus(newLoanTransaction.getApprovalStatus().name());
        }

        if(newLoanTransaction.getApprovedBy() != null) {
            build.setApprovedBy(newLoanTransaction.getApprovedBy());
        }

        if(newLoanTransaction.getApprovedAt() != null) {
            build.setApprovedAt(newLoanTransaction.getApprovedAt());
        }

        if(newLoanTransaction.getUpdatedAt() != null) {
            build.setUpdatedAt(newLoanTransaction.getUpdatedAt());
        }

        return build;
    }
}
