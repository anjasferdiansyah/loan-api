package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.ApiPath;
import com.enigmacamp.loanapp.dto.response.CommonResponse;
import com.enigmacamp.loanapp.entity.InstallmentType;
import com.enigmacamp.loanapp.service.InstallmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPath.INSTALLMENT_TYPE_URL)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
public class InstallmentTypeController {

    private final InstallmentTypeService installmentTypeService;


    @PostMapping
    public ResponseEntity<?> createInstallmentType(@RequestBody InstallmentType installmentType) {
        InstallmentType newInstallmentType = installmentTypeService.createNewInstallmentType(installmentType);

        CommonResponse<InstallmentType> response = CommonResponse.<InstallmentType>builder()
                .message("Installment type created")
                .status(HttpStatus.CREATED.value())
                .data(newInstallmentType)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<?> getAllInstallmentTypes() {
        List<InstallmentType> allInstallmentTypes = installmentTypeService.findAll();

        CommonResponse<List<InstallmentType>> response = CommonResponse.<List<InstallmentType>>builder()
                .message("Installment types found")
                .status(HttpStatus.OK.value())
                .data(allInstallmentTypes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping(ApiPath.GET_BY_ID)
    public ResponseEntity<?> getInstallmentTypeById(@PathVariable String id) {
        InstallmentType installmentType = installmentTypeService.findById(id);

        CommonResponse<InstallmentType> response = CommonResponse.<InstallmentType>builder()
                .message("Installment type found")
                .status(HttpStatus.OK.value())
                .data(installmentType)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateInstallmentType(@RequestBody InstallmentType installmentType) {
        InstallmentType updatedInstallmentType = installmentTypeService.update(installmentType);

        CommonResponse<InstallmentType> response = CommonResponse.<InstallmentType>builder()
                .message("Installment type updated")
                .status(HttpStatus.OK.value())
                .data(updatedInstallmentType)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteInstallmentType(@PathVariable String id) {
        installmentTypeService.delete(id);

        CommonResponse<?> response = CommonResponse.<InstallmentType>builder()
                .message("Installment type deleted")
                .status(HttpStatus.OK.value())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
