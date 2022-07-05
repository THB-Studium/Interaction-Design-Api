package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.angular.interactiondesignapi.models.Buchungstatus;
import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungUpdateTO {

    private UUID id;

    private String Buchungsnummer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate buchungDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hinFlugDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate ruckFlugDatum;

    private UUID mitReisenderId;

    private UUID buchungsklasseId;

    private String flughafen;

    private String handGepaeck;

    private String koffer;

    private ZahlungMethod zahlungMethod;

    private Buchungstatus status;

    private Boolean sendMail;

    private UUID reisenderId;

    private UUID reiseAngebotId;

}
