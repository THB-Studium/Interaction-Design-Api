package com.team.angular.interactiondesignapi.transfertobjects.erwartungen;

import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
    //private ReiseAngebot reiseAngebot; // Nur ID reicht
    private UUID ReiseAngebot_id;
}
