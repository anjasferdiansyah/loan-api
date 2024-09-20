package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.ApiPath;
import com.enigmacamp.loanapp.dto.response.CommonResponse;
import com.enigmacamp.loanapp.entity.InstallmentType;
import com.enigmacamp.loanapp.entity.LoanType;
import com.enigmacamp.loanapp.service.InstallmentTypeService;
import com.enigmacamp.loanapp.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPath.INSTALLMENT_TYPE_URL)
@RequiredArgsConstructor
public class LoanTypeController {

    private final LoanTypeService loanTypeService;


    @PostMapping
    public ResponseEntity<?> createInstallmentType(@RequestBody LoanType loanType) {
        LoanType newLoanType = loanTypeService.createNew(loanType);

        CommonResponse<LoanType> response = CommonResponse.<LoanType>builder()
                .message("Installment type created")
                .status(HttpStatus.CREATED.value())
                .data(newLoanType)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<?> getAllInstallmentTypes() {
        List<LoanType> allLoanTypes = loanTypeService.findAll();

        CommonResponse<List<LoanType>> response = CommonResponse.<List<LoanType>>builder()
                .message("Installment types found")
                .status(HttpStatus.OK.value())
                .data(allLoanTypes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping(ApiPath.GET_BY_ID)
    public ResponseEntity<?> getInstallmentTypeById(@PathVariable String id) {
        LoanType loanType = loanTypeService.findById(id);

        CommonResponse<LoanType> response = CommonResponse.<LoanType>builder()
                .message("Installment type found")
                .status(HttpStatus.OK.value())
                .data(loanType)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateInstallmentType(@RequestBody LoanType loanType) {

        LoanType updatedLoanType = loanTypeService.update(loanType);

        CommonResponse<LoanType> response = CommonResponse.<LoanType>builder()
                .message("Installment type updated")
                .status(HttpStatus.OK.value())
                .data(updatedLoanType)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteInstallmentType(@PathVariable String id) {
        loanTypeService.delete(id);

        CommonResponse<?> response = CommonResponse.<InstallmentType>builder()
                .message("Installment type deleted")
                .status(HttpStatus.OK.value())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}