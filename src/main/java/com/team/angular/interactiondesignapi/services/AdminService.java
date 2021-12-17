package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public Admin getAdmin(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + id));
    }

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    // update
    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public ResponseEntity<?> deleteAdmin(UUID id) {
        Admin actual = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + id));

        adminRepository.deleteById(actual.getId());
        log.info("admin successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    // update
    public Admin updateAdmin(Admin admin) {
        Admin _admin = getAdmin(admin.getId());

        if (admin.getName() != null)
            _admin.setName(admin.getName());
        if (admin.getEmail() != null)
            _admin.setEmail(admin.getEmail());
        if (admin.getKennwort() != null)
            _admin.setKennwort(admin.getKennwort());

        adminRepository.save(_admin);

        return _admin;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Admin admin = adminRepository.findAdminByName(name);
        if (admin != null) {
            //roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
            return new User(admin.getName(), admin.getKennwort(), null);
            //return new Admin(user.getUsername(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with the name " + name);
    }

}
