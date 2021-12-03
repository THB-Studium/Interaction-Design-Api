package com.team.angular.interactiondesignapi.transfertobjects.hightlights;

import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighlightReadListTO {
	
    private String name;

    private String description;
    
    private String bild;

    private LandReadTO land;

}
