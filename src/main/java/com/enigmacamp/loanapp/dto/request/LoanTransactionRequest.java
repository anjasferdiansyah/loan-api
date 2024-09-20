package com.enigmacamp.loanapp.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanTransactionRequest {
    private String loanTypeId;
    private String installmentTypeId;
    private String customerId;
    private Double nominal;
}
