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


        return TransactionResponse.builder()
                .id(newLoanTransaction.getId())
                .loanTypeId(newLoanTransaction.getLoanType().getId())
                .installmentTypeId(newLoanTransaction.getInstalmentType().getId())
                .customerId(newLoanTransaction.getCustomer().getId())
                .nominal(newLoanTransaction.getNominal())
                .approvedAt(newLoanTransaction.getApprovedAt())
                .approvalStatus(newLoanTransaction.getApprovalStatus().name())
                .approvedBy(newLoanTransaction.getApprovedBy())
                .transactionDetailResponse(list)
                .build();
    }
}
