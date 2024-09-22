package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.constant.ApiPath;
import com.enigmacamp.loanapp.dto.request.CustomerRequest;
import com.enigmacamp.loanapp.dto.request.CustomerUpdateRequest;
import com.enigmacamp.loanapp.dto.request.PagingRequest;
import com.enigmacamp.loanapp.dto.response.CommonResponse;
import com.enigmacamp.loanapp.dto.response.CustomerResponse;
import com.enigmacamp.loanapp.dto.response.PagingResponse;
import com.enigmacamp.loanapp.entity.Customer;
import com.enigmacamp.loanapp.service.CustomerService;
import com.enigmacamp.loanapp.util.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(ApiPath.CUSTOMERS_URL)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PutMapping
    public ResponseEntity<?> updateCustomerData(@RequestBody CustomerUpdateRequest request){

        Customer newCustomer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .status(Integer.toString(request.getStatus()))
                .build();

        Customer customer = customerService.createNewCustomer(newCustomer);

        CustomerResponse response = CustomerMapper.mapToCustomerResponse(customer);


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(ApiPath.GET_BY_ID)
    public ResponseEntity<?> getCustomerById(@PathVariable String id){
        Customer customer = customerService.getById(id);

        CustomerResponse customerResponse = CustomerMapper.mapToCustomerResponse(customer);

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("Get Customer Success")
                .status(HttpStatus.OK.value())
                .data(customerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomer (
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size
    ){
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<Customer> customers = customerService.getAll(request);

        Page<CustomerResponse> customerResponses = customers.map(CustomerMapper::mapToCustomerResponse);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(customers.getTotalElements())
                .totalPages(customers.getTotalPages())
                .build();


        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .message("Successfully get all customers")
                .status(HttpStatus.OK.value())
                .data(customerResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping(ApiPath.GET_BY_ID)
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        Customer customer = customerService.getById(id);
        customerService.delete(customer.getId());

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .message("Customer Has Been Deleted")
                .status(HttpStatus.OK.value())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }


}
