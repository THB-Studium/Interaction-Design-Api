package com.team.angular.interactiondesignapi.transfertobjects.erwartungen;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErwartungenReadWriteTO {
    private UUID id;
    private int abenteuer;
    private int entschleunigung;
    private int konfort;
    private int nachhaltigkeit;
    private int sonne_strand;
    private int sicherheit;
    private int road;
    private UUID reiseAngebotId;
}
