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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService implements UserDetailsService {//
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
            _admin.setName(admin.getName());
            _admin.setSurname(admin.getSurname());
            _admin.setPassword(bcryptEncoder.encode(admin.getPassword()));
            _admin.setEmail(admin.getEmail());
            _admin.setRole("ROLE_ADMIN");
            adminRepository.save(_admin);
            return new ResponseEntity<>("Successfully added", HttpStatus.OK);
        }
        return new ResponseEntity<>("Admin with email " +admin.getEmail(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> deleteAdmin(UUID id) {
        Admin actual = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + id));

        adminRepository.deleteById(actual.getId());
        log.info("admin successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    // todo: check email and return admin or bad request
    public Admin updateAdmin(Admin admin) {
        Admin _admin = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Admin with id: " + admin.getId()));

        if (admin.getName() != null)
            _admin.setName(admin.getName());
        if (admin.getSurname() != null)
            _admin.setSurname(admin.getSurname());
        if (admin.getEmail() != null)
            _admin.setEmail(admin.getEmail());
        if (admin.getPassword() != null)
            _admin.setPassword(bcryptEncoder.encode(admin.getPassword()));

        adminRepository.save(_admin);

        return _admin;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return Collections.singletonList(simpleGrantedAuthority);
    }


    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    //private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByEmail(email);


        return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(), true,
                true, true, true, getAuthorities());

    }
}
