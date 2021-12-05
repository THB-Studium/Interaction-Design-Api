package com.team.angular.interactiondesignapi.transfertobjects.hightlights;

import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
