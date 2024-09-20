package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.dto.request.PagingRequest;
import com.enigmacamp.loanapp.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface CustomerService {

    Customer createNewCustomer(Customer customer);
    Customer getById (String id);
    Page<Customer> getAll (PagingRequest request);
    Customer updateById(Customer customer);
    void delete(String id);
}
