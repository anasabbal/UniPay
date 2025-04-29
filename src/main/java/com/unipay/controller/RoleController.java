package com.unipay.controller;


import com.unipay.service.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Update user's role", description = "Updates the role of a user by their ID")
    @PatchMapping("/{userId}/role")
    public String updateUserRole(){
            return "null";
    }
}
