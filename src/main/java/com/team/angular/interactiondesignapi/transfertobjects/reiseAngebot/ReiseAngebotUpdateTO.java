package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReiseAngebotUpdateTO {
    private UUID id;

    private String titel;

    private Date startDatum;

    private Date endDatum;

    private int plaetze;

    private int freiPlaetze;

    private int interessiert;

    private Date anmeldungsFrist;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;
}
