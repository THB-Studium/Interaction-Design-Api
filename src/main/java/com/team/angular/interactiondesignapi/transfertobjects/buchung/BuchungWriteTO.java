package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungWriteTO {

    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate datum;

    private ReiserWriteTO mitReiser;

    private UUID buchungsklasseId;

    private String flughafen;

    private String handGepaeck;

    private String koffer;

    private ZahlungMethod zahlungMethod;

    private ReiserWriteTO reiser;

    private UUID reiseAngebotId;

}
