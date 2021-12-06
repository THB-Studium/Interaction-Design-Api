package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadListTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReiseAngebotReadTO {
    private UUID id;

    private String titel;

    private byte[] startbild;

    private Date startDatum;

    private Date endDatum;

    private int plaetze;

    private int freiPlaetze;

    private Date anmeldungsFrist;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;

    private List<BuchungsklassenReadListTO> buchungsklassenReadListTO;

    private ErwartungenReadListTO erwartungenReadListTO;

    private UUID land_id;

}
