package com.team.angular.interactiondesignapi.transfertobjects.land;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LandReadListTO {
    private UUID id;

    private String name;

    private String headerFarbe;

    private String bodyFarbe;

    private List<String> flughafen;

}
