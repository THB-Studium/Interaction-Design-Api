package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.repositories.AdminRepository;
import com.team.angular.interactiondesignapi.transfertobjects.admin.Admin2AdminOutTO;
import com.team.angular.interactiondesignapi.transfertobjects.admin.AdminOutTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    public AdminOutTO getAdmin(UUID id) {
        return Admin2AdminOutTO.apply(adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + id)));
    }

    public List<AdminOutTO> getAll() {
        return Admin2AdminOutTO.apply(adminRepository.findAll());
    }

    public ResponseEntity<?> addAdmin(Admin admin) {
        Admin _admin = new Admin();
        if (!adminRepository.existsAdminByEmail(admin.getEmail())) {
            _admin.setNachname(admin.getNachname());
            _admin.setVorname(admin.getVorname());
            _admin.setKennwort(bcryptEncoder.encode(admin.getKennwort()));
            _admin.setEmail(admin.getEmail());
            _admin.setRole("ROLE_ADMIN");
            return new ResponseEntity<>("Successfully added", HttpStatus.OK);
        }
        return new ResponseEntity<>("Admin with email admin.getEmail()", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> deleteAdmin(UUID id) {
        Admin actual = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + id));

        adminRepository.deleteById(actual.getId());
        log.info("admin successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public Admin updateAdmin(Admin admin) {
        Admin _admin = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + admin.getId()));

        if (admin.getNachname() != null)
            _admin.setNachname(admin.getNachname());
        if (admin.getVorname() != null)
            _admin.setVorname(admin.getVorname());
        if (admin.getEmail() != null)
            _admin.setEmail(admin.getEmail());
        if (admin.getKennwort() != null)
            _admin.setKennwort(bcryptEncoder.encode(admin.getKennwort()));

        adminRepository.save(_admin);

        return _admin;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;

        Admin admin = adminRepository.findAdminByName(name);
        if (admin != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(admin.getRole()));
            return new User(admin.getEmail(), admin.getKennwort(), roles);
            //return new Admin(user.getUsername(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with the name " + name);
    }

}
