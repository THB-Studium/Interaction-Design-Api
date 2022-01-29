package com.team.angular.interactiondesignapi.transfertobjects.land;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandWriteTO {

    private UUID id;

    private String name;

    private String headerFarbe;

    private String bodyFarbe;

    private List<String> flughafen;

    private String unterkunft_text;

    private String image;

}
