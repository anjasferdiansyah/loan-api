package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.dto.request.PagingRequest;
import com.enigmacamp.loanapp.entity.Customer;
import com.enigmacamp.loanapp.repository.CustomerRepository;
import com.enigmacamp.loanapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createNewCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    private Customer findByIdOrThrowNotFound(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }


    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }


    @Override
    public Page<Customer> getAll(PagingRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer updateById(Customer customer) {
        findByIdOrThrowNotFound(customer.getId());
        return customerRepository.save(customer);
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
