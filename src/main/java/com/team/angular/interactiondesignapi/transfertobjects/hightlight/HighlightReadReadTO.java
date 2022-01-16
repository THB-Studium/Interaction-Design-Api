package com.team.angular.interactiondesignapi.transfertobjects.hightlight;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighlightReadReadTO {
	
    private UUID id;
    
    private String name;
    
    private String description;
    
    private byte[] bild;
    
    private UUID landId;
}
