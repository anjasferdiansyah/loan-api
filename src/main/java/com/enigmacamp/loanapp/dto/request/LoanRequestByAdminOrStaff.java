package com.enigmacamp.loanapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequestByAdminOrStaff {
    private String loanTransactionId;
    private String interestRates;
}
