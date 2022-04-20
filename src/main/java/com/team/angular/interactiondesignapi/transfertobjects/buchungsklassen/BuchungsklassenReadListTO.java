package com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuchungsklassenReadListTO {
    private UUID id;
    private String type;
    private double preis;
    private String description;
}
