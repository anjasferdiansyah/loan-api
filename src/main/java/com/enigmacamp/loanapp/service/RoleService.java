package com.enigmacamp.loanapp.service;

import com.enigmacamp.loanapp.entity.Role;

public interface RoleService {
    Role getOrSave(Role role);
}
