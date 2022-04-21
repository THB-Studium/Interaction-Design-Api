package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.services.AdminService;
import com.team.angular.interactiondesignapi.transfertobjects.admin.AdminOutTO;
import com.team.angular.interactiondesignapi.transfertobjects.admin.AdminWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    protected AdminService adminService;

    @ApiOperation("Get All Admins")
    @GetMapping("")
    public List<AdminOutTO> getAllAdmins( @RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return adminService.getAll(pageNo, pageSize, sortBy);
    }

    @ApiOperation("Get One Admin")
    @GetMapping("/{id}")
    public AdminOutTO getAdminById(@ApiParam(name = "AdminId", value = "ID of the Admin") @PathVariable UUID id) {
        return adminService.getAdmin(id);
    }

    @ApiOperation("Add One Admin")
    @PostMapping("")
    public AdminOutTO addAdmin(
            @ApiParam(name = "Admin", value = "Admin to add") @RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @ApiOperation("Update Admin")
    @PutMapping("")
    public AdminOutTO updateAdmin(@ApiParam(name = "AdminWriteTO", value = "Admin to update") @RequestBody AdminWriteTO admin) {
        return adminService.updateAdmin(admin);
    }

    @ApiOperation("Delete Admin")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@ApiParam(name = "AdminId", value = "Id of the Admin") @PathVariable UUID id) {
        return adminService.deleteAdmin(id);
    }

}
