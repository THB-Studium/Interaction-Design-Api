package com.team.angular.interactiondesignapi.transfertobjects.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOutTO {
    private UUID id;

    private String name;
    private String surname;
    private String email;
}
