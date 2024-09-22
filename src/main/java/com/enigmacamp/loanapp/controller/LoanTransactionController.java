package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.ApiPath;
import com.enigmacamp.loanapp.constant.EApprovalStatus;
import com.enigmacamp.loanapp.dto.request.LoanRequestByAdminOrStaff;
import com.enigmacamp.loanapp.dto.request.LoanTransactionRequest;
import com.enigmacamp.loanapp.dto.response.CommonResponse;
import com.enigmacamp.loanapp.dto.response.TransactionDetailResponse;
import com.enigmacamp.loanapp.dto.response.TransactionResponse;
import com.enigmacamp.loanapp.entity.LoanTransaction;
import com.enigmacamp.loanapp.service.CustomerService;
import com.enigmacamp.loanapp.service.InstallmentTypeService;
import com.enigmacamp.loanapp.service.LoanTransactionService;
import com.enigmacamp.loanapp.service.LoanTypeService;
import com.enigmacamp.loanapp.util.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiPath.LOAN_TRANSACTION_URL)
@RequiredArgsConstructor
public class LoanTransactionController {

    private final LoanTransactionService loanTransactionService;



    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> requestLoanTransaction(@RequestBody LoanTransactionRequest loanTransaction) {

        LoanTransaction newLoanTransaction = loanTransactionService.createNew(loanTransaction);

        TransactionResponse transactionResponse = TransactionMapper.mapToLoanTransactionResponse(newLoanTransaction);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .message("Successfully created new loan transaction")
                .status(HttpStatus.CREATED.value())
                .data(transactionResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }



    @GetMapping(ApiPath.GET_BY_ID)
    public ResponseEntity<?> getLoanTransactionById(@PathVariable String id) {
        LoanTransaction loanTransaction = loanTransactionService.findById(id);

        TransactionResponse transactionResponse = TransactionMapper.mapToLoanTransactionResponse(loanTransaction);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .message("Successfully retrieved loan transaction")
                .status(HttpStatus.OK.value())
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> updateLoanTransaction(@RequestBody LoanRequestByAdminOrStaff loanTransaction) {

        LoanTransaction update = loanTransactionService.update(loanTransaction);

        TransactionResponse transactionResponse = TransactionMapper.mapToLoanTransactionResponse(update);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .message("Successfully updated loan transaction")
                .status(HttpStatus.OK.value())
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLoanTransaction(@PathVariable String id) {
        loanTransactionService.delete(id);
        CommonResponse<?> response = CommonResponse.<LoanTransaction>builder()
                .message("Successfully deleted loan transaction")
                .status(HttpStatus.OK.value())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
