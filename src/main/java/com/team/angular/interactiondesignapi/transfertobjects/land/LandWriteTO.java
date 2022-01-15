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

    private List<String> flugHafen;

    private String unterkunft_text;

}
