package com.team.angular.interactiondesignapi.transfertobjects.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOutTO {
    private UUID id;

    private String name;
    private String surname;
    @Email
    private String email;
}
