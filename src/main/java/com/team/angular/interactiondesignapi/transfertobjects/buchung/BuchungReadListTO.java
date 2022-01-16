package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungReadListTO {

    // todo: i think we don't need it; we need only BuchungReadTO

    private UUID id;

    private Date datum;

    private UUID mitReiserId;

    private UUID buchungsklasseId;

    private String flughafen;

    private String handGepaeck;

    private String koffer;

    private ZahlungMethod zahlungMethod;

    private UUID reiserId;

    private UUID reiseAngebotId;

}
