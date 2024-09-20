package com.enigmacamp.loanapp.util.mapper;

import com.enigmacamp.loanapp.dto.response.CustomerResponse;
import com.enigmacamp.loanapp.entity.Customer;

public class CustomerMapper {
    public static CustomerResponse mapToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .dateOfBirth(customer.getDateOfBirth())
                .status(customer.getStatus())
                .build();
    }
}
