package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.repositories.AdminRepository;
import com.team.angular.interactiondesignapi.transfertobjects.reponse.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin getAdmin(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + id));
    }

    public ResponseEntity<?> deleteAdmin(UUID id) {
        Admin actual = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + id));

        adminRepository.deleteById(actual.getId());
        log.info("admin successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    //UPDATE

    // ecraser
    public Admin updateAdmin(Admin admin, UUID id) {
        Admin _admin = getAdmin(id);
        _admin = admin;
        return adminRepository.save(_admin);
    }

    // check les elements qui sont envoyes un par un
    public ResponseEntity<?> updateAdminElement(UUID id, Map<String, Object> admin_new) {
        Admin admin = getAdmin(id);

        admin_new.forEach((element, value) -> {
            switch (element) {
                case "name":
                    admin.setName((String) value);
                    break;
                case "kennwort":
                    admin.setKennwort((String) value);
                    break;
                case "email":
                    admin.setEmail((String) value);
                    break;
            }
        });

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Admin>> violations = validator.validate(admin);// , OnUpdate.class);

        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse(violations.toString()));
        }

        addAdmin(admin);
        return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    }

}
