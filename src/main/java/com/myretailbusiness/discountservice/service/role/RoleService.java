package com.myretailbusiness.discountservice.service.role;

import com.myretailbusiness.discountservice.domain.Role;
import com.myretailbusiness.discountservice.exception.NoResourceFoundException;
import com.myretailbusiness.discountservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleService {
    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role findByName(String name) {
        return repository.findByName(name).orElseThrow(()
                -> new NoResourceFoundException("Role: " + name + " doesn't exist."));
    }
}
