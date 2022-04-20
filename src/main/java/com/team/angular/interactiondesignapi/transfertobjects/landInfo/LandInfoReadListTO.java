package com.team.angular.interactiondesignapi.transfertobjects.landInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LandInfoReadListTO {

    private UUID id;
    private String titel;
    @Lob
    private String description;

}
