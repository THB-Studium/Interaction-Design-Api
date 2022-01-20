package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReiseAngebotReadListTO {
    private UUID id;

    private String titel;

    private byte[] startbild;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDatum;

    private int plaetze;

    private int freiPlaetze;

    private int interessiert;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate anmeldungsFrist;

    // not necessary for the workflow
    /*@ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;*/
}
