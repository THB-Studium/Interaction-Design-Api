package com.team.angular.interactiondesignapi.transfertobjects.hightlight;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighlightReadReadTO {
	
    private UUID id;
    
    private String name;

    @Lob
    private String description;
    
    private byte[] bild;
    
    private UUID landId;
}
