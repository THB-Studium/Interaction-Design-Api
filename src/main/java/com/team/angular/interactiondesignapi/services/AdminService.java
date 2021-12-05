package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Admin updateAdmin(Admin admin) {
        Admin admin_ = getAdmin(admin.getId());

        if (admin.getName() != null)
            admin_.setName(admin.getName());
        if (admin.getEmail() != null)
            admin_.setEmail(admin.getEmail());
        if (admin.getKennwort() != null)
            admin_.setKennwort(admin.getKennwort());
        return admin_;
    }

    /*public ResponseEntity<?> updateAdminElement(UUID id, Admin admin_new) {
        Admin admin = getAdmin(id);
        //Map<String, Object> map = oMapper.convertValue(admin, Map.class);

        //Map<String, Object> admin_new = admin;
        admin_new.forEach((element, value) -> {
            switch (element) {
                case "name":
                    if (value != null)
                        admin.setName((String) value);
                    break;
                case "kennwort":
                    if (value != null)
                        admin.setKennwort((String) value);
                    break;
                case "email":
                    if (value != null)
                        admin.setEmail((String) value);
                    break;
            }
        });

        Validator validator =
                Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Admin>> violations = validator.validate(admin);

        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(new
                    MessageResponse(violations.toString()));
        }

        addAdmin(admin);
        return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    }*/
}
