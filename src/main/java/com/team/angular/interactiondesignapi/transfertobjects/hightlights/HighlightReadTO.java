package com.team.angular.interactiondesignapi.transfertobjects.hightlights;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighlightReadTO {
	
    private UUID id;
    
    private String name;
    
    private String description;
    
    private byte[] bild;
    
    private UUID Land_id;
}
