package com.team.angular.interactiondesignapi.transfertobjects.erwartungen;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErwartungenReadTO {
    private UUID id;
    private int abenteuer;
    private int entschleunigung;
    private int konfort;
    private int nachhaltigkeit;
    private int sonne_strand;
    private int sicherheit;
    private int road;
    private UUID ReiseAngebot_id;
}
