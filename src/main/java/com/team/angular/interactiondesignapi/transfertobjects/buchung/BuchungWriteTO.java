package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.angular.interactiondesignapi.models.Buchungstatus;
import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderWriteTO;
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

    private String Buchungsnummer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate buchungDatum;

    private ReisenderWriteTO mitReisender;

    private UUID buchungsklasseId;

    private String abFlughafenReisender;
    private String abFlughafenMitReisender;

    private String ruckFlughafenReisender;
    private String ruckFlughafenMitReisender;

    private Boolean handGepaeckReisender;
    private Boolean handGepaeckMitReisender;

    private String kofferReisender;
    private String kofferMitReisender;

    private ZahlungMethod zahlungMethod;

    private Buchungstatus status;

    private ReisenderWriteTO reisender;

    private UUID reiseAngebotId;

}
