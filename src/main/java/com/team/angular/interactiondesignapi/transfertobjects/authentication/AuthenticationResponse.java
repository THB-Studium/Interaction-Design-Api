package com.team.angular.interactiondesignapi.transfertobjects.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String email;
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
