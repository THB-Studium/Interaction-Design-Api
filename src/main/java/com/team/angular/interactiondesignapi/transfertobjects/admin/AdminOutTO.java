package com.team.angular.interactiondesignapi.transfertobjects.admin;

import lombok.*;

import javax.validation.constraints.Email;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOutTO {
    private UUID id;

    private String name;
    private String surname;
    @Email
    private String email;
}
