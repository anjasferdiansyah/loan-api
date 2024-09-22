package com.enigmacamp.loanapp.service.impl;

import com.enigmacamp.loanapp.entity.Staff;
import com.enigmacamp.loanapp.repository.StaffRepository;
import com.enigmacamp.loanapp.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public Staff createNew(Staff staff) {
        return staffRepository.saveAndFlush(staff);
    }

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    public Staff findByIdOrThrow(String id) {
        return staffRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
    }

    @Override
    public Staff findById(String id) {
        return findByIdOrThrow(id);
    }

    @Override
    public Staff update(Staff staff) {
        findById(staff.getId());
        return staffRepository.save(staff);
    }

    @Override
    public void delete(String id) {
        findById(id);
        staffRepository.deleteById(id);
    }
}
