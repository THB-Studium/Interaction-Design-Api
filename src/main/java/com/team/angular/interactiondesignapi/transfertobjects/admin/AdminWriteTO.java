package com.team.angular.interactiondesignapi.transfertobjects.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminWriteTO {

    private UUID id;
    private String name;
    private String surname;
    private String oldPassword;
    private String newPassword;
    private String email;

}