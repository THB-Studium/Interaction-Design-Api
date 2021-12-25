package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.AdminService;
import com.team.angular.interactiondesignapi.services.authentication.JwtUtil;
import com.team.angular.interactiondesignapi.transfertobjects.authentication.AuthenticationRequest;
import com.team.angular.interactiondesignapi.transfertobjects.authentication.AuthenticationResponse;
import io.jsonwebtoken.impl.DefaultClaims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/adminPanel")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation("Authentication")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@ApiParam(name = "AuthenticationRequest",
            value = "authentication for admin") @RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            // authenticate the user with email and Password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        // use loadUserByUsername with email to find user by email
        UserDetails userdetails = adminService.loadUserByUsername(authenticationRequest.getEmail());
        String token = jwtUtil.generateToken(userdetails);
        return ResponseEntity.ok(new AuthenticationResponse(authenticationRequest.getEmail(), token));
    }

    @GetMapping(value = "/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        String token = jwtUtil.doGenerateRefreshToken(claims);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}
