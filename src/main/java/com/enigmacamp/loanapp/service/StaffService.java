package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.entity.Staff;

import java.util.List;

public interface StaffService {
    Staff createNew(Staff staff);
    List<Staff> findAll();
    Staff findById(String  id);
    Staff update(Staff staff);
    void delete(String id);
}
