package com.team.angular.interactiondesignapi.transfertobjects.landInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandInfoReadWriteTO {
    private UUID id;
    private String titel;
    @Lob
    private String description;
    private UUID landId;
}
