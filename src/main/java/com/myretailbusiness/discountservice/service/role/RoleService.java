package com.myretailbusiness.discountservice.service.role;

import com.myretailbusiness.discountservice.domain.Role;

public interface RoleService {
    Role findByName(String name);
}
