package com.team.angular.interactiondesignapi.transfertobjects.landInfo;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandInfoReadListTO {
    private UUID id;
    private String titel;
    @Lob
    private String description;
}
