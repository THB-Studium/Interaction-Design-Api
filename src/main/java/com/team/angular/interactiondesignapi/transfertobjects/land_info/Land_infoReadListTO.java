package com.team.angular.interactiondesignapi.transfertobjects.land_info;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Land_infoReadListTO {
    private UUID id;
    private String titel;
    @Lob
    private String description;
}
