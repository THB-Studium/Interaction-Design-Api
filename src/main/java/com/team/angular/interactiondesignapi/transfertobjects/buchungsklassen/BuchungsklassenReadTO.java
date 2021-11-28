package com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen;

import java.util.UUID;

import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungsklassenReadTO {
	
    private UUID id;

    private  String type;
    
    private double preis;

    private LandReadTO land;

}
