package com.team.angular.interactiondesignapi.transfertobjects.infoLand;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoLandReadListTO {
	
    private UUID id;

    private String titel;
    private String beschreibung;
}
