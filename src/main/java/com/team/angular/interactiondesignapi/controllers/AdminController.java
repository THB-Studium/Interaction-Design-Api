package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.services.AdminService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public class AdminController {
    @Autowired
    protected AdminService adminService;

    @ApiOperation("Get All Admins")
    @GetMapping("")
    public List<Admin> getAllAdmins() {
        return adminService.getAll();
    }

    @ApiOperation("Get One Admin")
    @GetMapping("/{id}")
    public Admin getAdminById(@ApiParam(name = "AdminId", value = "get One Admin") @PathVariable UUID id) {
        return adminService.getAdmin(id);
    }

    @ApiOperation("Add One Admin")
    @PostMapping("")
    public Admin addAdmin(
            @ApiParam(name = "Admin", value = "Admin to add") @RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @ApiOperation("Update Admin")
    @PutMapping("")
    public Admin updateAdmin(@ApiParam(name = "Admin", value = "Admin to update") @RequestBody Admin admin) {
        return adminService.updateAdmin(admin);
    }

    @ApiOperation("Delete Admin")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteAdmin(@ApiParam(name = "AdminId", value = "Id of the Admin") @PathVariable UUID id) {
        return adminService.deleteAdmin(id);
    }

}
