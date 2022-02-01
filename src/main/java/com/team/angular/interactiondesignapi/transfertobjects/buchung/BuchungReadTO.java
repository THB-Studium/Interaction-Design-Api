package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungReadTO {

    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate datum;

    private UUID mitReiserId;

    private UUID buchungsklasseId;

    private String flughafen;

    private String handGepaeck;

    private String koffer;

    private ZahlungMethod zahlungMethod;

    private UUID reiserId;

    private UUID reiseAngebotId;

}
