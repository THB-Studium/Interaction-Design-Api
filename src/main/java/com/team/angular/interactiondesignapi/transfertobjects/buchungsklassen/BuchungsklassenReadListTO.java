package com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungsklassenReadListTO {
    private UUID id;
    private String type;
    private double preis;
    private String description;
}
