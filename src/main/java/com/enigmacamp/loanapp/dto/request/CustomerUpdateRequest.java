package com.enigmacamp.loanapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CustomerUpdateRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private Date dateOfBirth;
    private Integer status;
}
