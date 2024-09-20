package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.ApiPath;
import com.enigmacamp.loanapp.constant.EApprovalStatus;
import com.enigmacamp.loanapp.dto.request.LoanRequestByAdminOrStaff;
import com.enigmacamp.loanapp.dto.request.LoanTransactionRequest;
import com.enigmacamp.loanapp.dto.response.CommonResponse;
import com.enigmacamp.loanapp.entity.LoanTransaction;
import com.enigmacamp.loanapp.service.CustomerService;
import com.enigmacamp.loanapp.service.InstallmentTypeService;
import com.enigmacamp.loanapp.service.LoanTransactionService;
import com.enigmacamp.loanapp.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiPath.LOAN_TRANSACTION_URL)
@RequiredArgsConstructor
public class LoanTransactionController {

    private final LoanTransactionService loanTransactionService;


    @PostMapping
    public ResponseEntity<?> requestLoanTransaction(@RequestBody LoanTransactionRequest loanTransaction) {

        LoanTransaction newLoanTransaction = loanTransactionService.createNew(loanTransaction);

        CommonResponse<LoanTransaction> response = CommonResponse.<LoanTransaction>builder()
                .message("Successfully created new loan transaction")
                .status(HttpStatus.CREATED.value())
                .data(newLoanTransaction)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping(ApiPath.GET_BY_ID)
    public ResponseEntity<?> getLoanTransactionById(@PathVariable String id) {
        LoanTransaction loanTransaction = loanTransactionService.findById(id);

        CommonResponse<LoanTransaction> response = CommonResponse.<LoanTransaction>builder()
                .message("Successfully retrieved loan transaction")
                .status(HttpStatus.OK.value())
                .data(loanTransaction)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateLoanTransaction(@RequestBody LoanRequestByAdminOrStaff loanTransaction) {

        LoanTransaction update = loanTransactionService.update(loanTransaction);

        CommonResponse<LoanTransaction> response = CommonResponse.<LoanTransaction>builder()
                .message("Successfully updated loan transaction")
                .status(HttpStatus.OK.value())
                .data(update)
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
