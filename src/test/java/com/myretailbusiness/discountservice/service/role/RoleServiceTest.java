package com.myretailbusiness.discountservice.service.role;

import com.myretailbusiness.discountservice.domain.Role;
import com.myretailbusiness.discountservice.exception.NoResourceFoundException;
import com.myretailbusiness.discountservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.myretailbusiness.discountservice.service.role.RoleServiceTestUtil.getRole;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock private RoleRepository roleRepository;

    @InjectMocks private RoleServiceImpl roleService;

    @Test public void Should_ReturnRole_When_RoleNameFound() {
        Role expectedRole = getRole();
        when(roleRepository.findByName(expectedRole.getName()))
                .thenReturn(Optional.of(expectedRole));
        Role actualRole = roleService.findByName(expectedRole.getName());
        assertEquals(expectedRole.getName(), actualRole.getName());
        verify(roleRepository, times(1)).findByName(expectedRole.getName());
    }

    @Test public void Should_ThrowNoResourceFoundException_When_RoleNameNotFound() {
        Role expectedRole = getRole();
        when(roleRepository.findByName(expectedRole.getName()))
                .thenReturn(Optional.empty());
        assertThrows(NoResourceFoundException.class, () -> roleService.findByName(expectedRole.getName()));
        verify(roleRepository, times(1)).findByName(expectedRole.getName());
    }
}
