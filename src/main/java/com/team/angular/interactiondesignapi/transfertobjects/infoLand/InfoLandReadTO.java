package com.team.angular.interactiondesignapi.transfertobjects.infoLand;

import java.util.UUID;

import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoLandReadTO {
	
    private UUID id;

    private String titel;
    private String beschreibung;

    private LandReadTO landReadTO;

}
