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

import javax.validation.constraints.NotBlank;

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

    @NotBlank
    private String abFlughafenReisender;
    @NotBlank
    private String abFlughafenMitReisender;

    @NotBlank
    private String ruckFlughafenReisender;
    @NotBlank
    private String ruckFlughafenMitReisender;

    @NotBlank
    private Boolean handGepaeckReisender;
    @NotBlank
    private Boolean handGepaeckMitReisender;

    @NotBlank
    private String kofferReisender;
    @NotBlank
    private String kofferMitReisender;

    private ZahlungMethod zahlungMethod;

    private Buchungstatus status;

    private Boolean sendMail;

    private ReisenderWriteTO reisender;

    private UUID reiseAngebotId;

}
