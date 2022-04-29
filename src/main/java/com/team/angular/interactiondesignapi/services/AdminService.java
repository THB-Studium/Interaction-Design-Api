package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.repositories.AdminRepository;
import com.team.angular.interactiondesignapi.transfertobjects.admin.Admin2AdminOutTO;
import com.team.angular.interactiondesignapi.transfertobjects.admin.AdminOutTO;
import com.team.angular.interactiondesignapi.transfertobjects.admin.AdminWriteTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                .orElseThrow(() -> new ApiRequestException("Cannot find Admin with id: " + id)));
    }

    public List<AdminOutTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Admin> pagedResult = adminRepository.findAll(paging);

        return Admin2AdminOutTO.apply(pagedResult.getContent());
    }

    public AdminOutTO addAdmin(Admin admin) {
        Admin _admin = new Admin();

        if (!adminRepository.existsAdminByEmail(admin.getEmail())) {
            _admin.setName(admin.getName());
            _admin.setSurname(admin.getSurname());
            _admin.setPassword(bcryptEncoder.encode(admin.getPassword()));
            _admin.setEmail(admin.getEmail());
            _admin.setRole("ROLE_ADMIN");
            _admin.setCreationDate(LocalDate.now());
            return Admin2AdminOutTO.apply(adminRepository.save(_admin));
        } else {
            throw new ApiRequestException("Email has already been taken");
        }
    }

    public ResponseEntity<?> deleteAdmin(UUID id) {
        Admin actual = adminRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Admin with id: " + id));

        adminRepository.deleteById(actual.getId());
        log.info("admin successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public AdminOutTO updateAdmin(AdminWriteTO admin) {
        Admin _admin = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new ApiRequestException("Cannot find Admin with id: " + admin.getId()));

        if (admin.getName() != null)
            _admin.setName(admin.getName());
        if (admin.getSurname() != null)
            _admin.setSurname(admin.getSurname());

        // control if new email does not exist
        if (admin.getEmail() != null && !_admin.getEmail().equals(admin.getEmail())) {
            if (!Objects.equals(admin.getEmail(), _admin.getEmail())
                    && !adminRepository.existsAdminByEmail(admin.getEmail())) {
                _admin.setEmail(admin.getEmail());
            } else {
                throw new ApiRequestException("Email has already been taken");
            }
        }

        // control if the old password is correct before set the new
        if (admin.getNewPassword() != null && admin.getOldPassword() != null) {
            if (bcryptEncoder.matches(admin.getOldPassword(), _admin.getPassword())) {
                _admin.setPassword(bcryptEncoder.encode(admin.getNewPassword()));
            } else {
                throw new ApiRequestException("The Old password is invalid");
            }
        } else if ((admin.getNewPassword() != null && admin.getOldPassword() == null) ||
                (admin.getNewPassword() == null && admin.getOldPassword() != null)) {
            throw new ApiRequestException("both old and new passwords are required");
        }

        _admin.setUpdateDate(LocalDate.now());

        adminRepository.save(_admin);

        return Admin2AdminOutTO.apply(_admin);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // load admin with email and not with name
        Admin admin = adminRepository.findAdminByEmail(email);

        // Initialise User with email on place of name
        return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(), true,
                true, true, true, getAuthorities());
    }
}
