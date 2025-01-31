package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.ApiPath;
import com.enigmacamp.loanapp.dto.response.CommonResponse;
import com.enigmacamp.loanapp.entity.InstallmentType;
import com.enigmacamp.loanapp.entity.LoanType;
import com.enigmacamp.loanapp.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPath.LOAN_TYPE_URL)
@RequiredArgsConstructor

public class LoanTypeController {

    private final LoanTypeService loanTypeService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @PostMapping
    public ResponseEntity<?> createLoanTypes(@RequestBody LoanType loanType) {
        LoanType newLoanType = loanTypeService.createNew(loanType);

        CommonResponse<LoanType> response = CommonResponse.<LoanType>builder()
                .message("Installment type created")
                .status(HttpStatus.CREATED.value())
                .data(newLoanType)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<?> getAllLoanTypes() {
        List<LoanType> allLoanTypes = loanTypeService.findAll();

        CommonResponse<List<LoanType>> response = CommonResponse.<List<LoanType>>builder()
                .message("Installment types found")
                .status(HttpStatus.OK.value())
                .data(allLoanTypes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping(ApiPath.GET_BY_ID)
    public ResponseEntity<?> getLoanTypeById(@PathVariable String id) {
        LoanType loanType = loanTypeService.findById(id);

        CommonResponse<LoanType> response = CommonResponse.<LoanType>builder()
                .message("Installment type found")
                .status(HttpStatus.OK.value())
                .data(loanType)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @PutMapping
    public ResponseEntity<?> updateLoanType(@RequestBody LoanType loanType) {

        LoanType updatedLoanType = loanTypeService.update(loanType);

        CommonResponse<LoanType> response = CommonResponse.<LoanType>builder()
                .message("Installment type updated")
                .status(HttpStatus.OK.value())
                .data(updatedLoanType)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @DeleteMapping(ApiPath.GET_BY_ID)
    public ResponseEntity<?> deleteLoanType(@PathVariable String id) {
        loanTypeService.delete(id);

        CommonResponse<?> response = CommonResponse.<InstallmentType>builder()
                .message("Installment type deleted")
                .status(HttpStatus.OK.value())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}