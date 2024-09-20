package com.enigmacamp.loanapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private String customerId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phone;
    private String status;
}
