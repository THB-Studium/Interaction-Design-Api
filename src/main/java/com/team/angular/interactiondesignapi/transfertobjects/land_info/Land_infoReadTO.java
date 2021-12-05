package com.team.angular.interactiondesignapi.transfertobjects.land_info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Land_infoReadTO {
    private UUID id;
    private String titel;
    @Lob
    private String description;
    private UUID land_id;
}
