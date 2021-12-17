package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.services.AdminService;
import com.team.angular.interactiondesignapi.services.authentication.JwtUtil;
import com.team.angular.interactiondesignapi.transfertobjects.authentication.AuthenticationRequest;
import com.team.angular.interactiondesignapi.transfertobjects.authentication.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            // authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getName(), authenticationRequest.getKennwort()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userdetails = adminService.loadUserByUsername(authenticationRequest.getName());
        String token = jwtUtil.generateToken(userdetails);
        return ResponseEntity.ok(new AuthenticationResponse(authenticationRequest.getName(),token));
    }

    // todo: we need it? i think nope
    /*
    @PostMapping(value = "/register")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin) throws Exception {
        adminService.addAdmin(admin);
        return ResponseEntity.ok("REGISTRATION SUCCESSFUL");
    }*/

}
