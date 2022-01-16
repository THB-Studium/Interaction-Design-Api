package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungWriteTO {

    // todo: don't need id by add
    //private UUID id;

    private Date datum;

    private ReiserWriteTO mitReiser;

    private UUID buchungsklasseId;

    private String flugHafen;

    private String handGepaeck;

    private String koffer;

    private ZahlungMethod zahlungMethod;

    private ReiserWriteTO reiser;

    private UUID reiseAngebotId;

}
